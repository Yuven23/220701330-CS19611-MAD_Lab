// DatabaseHelper.kt
package com.example.githelp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import org.json.JSONObject

// Extension function to load JSON from assets
private fun Context.loadJSONFromAsset(filename: String): String? { // Made 'private'
    return try {
        assets.open(filename).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        e.printStackTrace()
        return null // Explicitly return null in case of error
    }
}

class DatabaseHelper(private val context: Context) : // context as a private val
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "GitCommandExplorer.db"
        private const val DATABASE_VERSION = 1

        // Table Names
        private const val TABLE_PRIMARY_OPTIONS = "primary_options"
        private const val TABLE_SECONDARY_OPTIONS = "secondary_options"

        // Primary Options Table Columns
        private const val COL_PRIMARY_VALUE = "value"
        private const val COL_PRIMARY_LABEL = "label"

        // Secondary Options Table Columns
        private const val COL_SECONDARY_VALUE = "value"
        private const val COL_SECONDARY_LABEL = "label"
        private const val COL_SECONDARY_USAGE = "usage"
        private const val COL_SECONDARY_NB = "nb"
        private const val COL_SECONDARY_PRIMARY_VALUE = "primary_value" // Foreign Key
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create Primary Options Table
        val createPrimaryTable = """
            CREATE TABLE $TABLE_PRIMARY_OPTIONS (
                $COL_PRIMARY_VALUE TEXT PRIMARY KEY,
                $COL_PRIMARY_LABEL TEXT
            )
        """.trimIndent()
        db?.execSQL(createPrimaryTable)

        // Create Secondary Options Table
        val createSecondaryTable = """
            CREATE TABLE $TABLE_SECONDARY_OPTIONS (
                $COL_SECONDARY_VALUE TEXT PRIMARY KEY,
                $COL_SECONDARY_LABEL TEXT,
                $COL_SECONDARY_USAGE TEXT,
                $COL_SECONDARY_NB TEXT,
                $COL_SECONDARY_PRIMARY_VALUE TEXT,
                FOREIGN KEY ($COL_SECONDARY_PRIMARY_VALUE)
                REFERENCES $TABLE_PRIMARY_OPTIONS($COL_PRIMARY_VALUE)
            )
        """.trimIndent()
        db?.execSQL(createSecondaryTable)

        // ** POPULATING DATABASE FROM JSON **
//        populateDatabase(db)
//        Log.i("DB_INIT", "Database populated on create.")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades by dropping existing tables
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SECONDARY_OPTIONS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRIMARY_OPTIONS")
        onCreate(db) // Re-create and re-populate on upgrade (for simplicity)
    }

    private fun populateDatabase(db: SQLiteDatabase?) {
        try {
            val jsonString = context.loadJSONFromAsset("git_command_explorer.json")
            jsonString?.let { jsonStringNonNull -> // Renamed 'it' to 'jsonStringNonNull'
                val json = JSONObject(jsonStringNonNull)
                val primaryOptions = json.getJSONArray("primary_options")

                for (i in 0 until primaryOptions.length()) {
                    val primary = primaryOptions.getJSONObject(i)
                    val values = ContentValues().apply {
                        put(COL_PRIMARY_VALUE, primary.getString("value"))
                        put(COL_PRIMARY_LABEL, primary.getString("label"))
                    }
                    db?.insert(TABLE_PRIMARY_OPTIONS, null, values)
                }

                val secondaryOptions = json.getJSONObject("secondary_options")
                val primaryKeys = secondaryOptions.keys()
                while (primaryKeys.hasNext()) {
                    val primaryKey = primaryKeys.next() as String
                    val secondaryArray = secondaryOptions.getJSONArray(primaryKey)
                    for (i in 0 until secondaryArray.length()) {
                        val secondary = secondaryArray.getJSONObject(i)
                        val values = ContentValues().apply {
                            put(COL_SECONDARY_VALUE, secondary.getString("value"))
                            put(COL_SECONDARY_LABEL, secondary.getString("label"))
                            if (secondary.has("usage")) {
                                put(COL_SECONDARY_USAGE, secondary.getString("usage"))
                            }
                            if (secondary.has("nb")) {
                                put(COL_SECONDARY_NB, secondary.getString("nb"))
                            }
                            put(COL_SECONDARY_PRIMARY_VALUE, primaryKey)
                        }
                        db?.insert(TABLE_SECONDARY_OPTIONS, null, values)
                    }
                }
            } ?: run {
                Log.e("DB_INIT", "Error loading JSON for population.")
            }
        } catch (e: Exception) {
            Log.e("DB_INIT", "Error populating database: ${e.localizedMessage}")
        }
    }

    // ** The app will use these methods to retrieve data from the database **
    fun getPrimaryOptions(): List<PrimaryOptions> {
        val primaryOptionsList = mutableListOf<PrimaryOptions>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_PRIMARY_OPTIONS,
            arrayOf(COL_PRIMARY_VALUE, COL_PRIMARY_LABEL),
            null, null, null, null, null
        )
        cursor.use {
            while (it.moveToNext()) {
                primaryOptionsList.add(
                    PrimaryOptions(
                        it.getString(it.getColumnIndexOrThrow(COL_PRIMARY_VALUE)),
                        it.getString(it.getColumnIndexOrThrow(COL_PRIMARY_LABEL))
                    )
                )
            }
        }
        db.close()
        return primaryOptionsList
    }

    fun getSecondaryOptions(primaryValue: String): List<SecondaryOptions> {
        val secondaryOptionsList = mutableListOf<SecondaryOptions>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_SECONDARY_OPTIONS,
            arrayOf(
                COL_SECONDARY_VALUE,
                COL_SECONDARY_LABEL,
                COL_SECONDARY_USAGE,
                COL_SECONDARY_NB
            ),
            "$COL_SECONDARY_PRIMARY_VALUE = ?",
            arrayOf(primaryValue),
            null, null, null
        )
        cursor.use {
            while (it.moveToNext()) {
                secondaryOptionsList.add(
                    SecondaryOptions(
                        it.getString(it.getColumnIndexOrThrow(COL_SECONDARY_VALUE)),
                        it.getString(it.getColumnIndexOrThrow(COL_SECONDARY_LABEL)),
                        it.getString(it.getColumnIndexOrThrow(COL_SECONDARY_USAGE)) ?: "",
                        it.getString(it.getColumnIndexOrThrow(COL_SECONDARY_NB)) ?: ""
                    )
                )
            }
        }
        db.close()
        return secondaryOptionsList
    }

    override fun close() {
        readableDatabase.close()
        writableDatabase.close()
        super.close()
    }
}