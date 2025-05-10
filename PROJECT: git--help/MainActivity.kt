// MainActivity.kt
package com.example.githelp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.githelp.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var dataBind: ActivityMainBinding
    private var primaryOptions = ArrayList<PrimaryOptions>()
    private var primaryOptionsValue = ""
    private var secondaryOptions = ArrayList<SecondaryOptions>()
    private lateinit var dbHelper: DatabaseHelper
    private var usage = ""
    private var note = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBind.textGitCommand.text =
            Html.fromHtml(resources.getString(R.string.git_command_explorer))

        dbHelper = DatabaseHelper(this)
        loadPrimaryOptionsFromDB()

        dataBind.inputFirstField.setOnTouchListener { _, _ ->
            dataBind.inputFirstField.showDropDown()
            false
        }
        dataBind.inputSecondField.setOnTouchListener { _, _ ->
            dataBind.inputSecondField.showDropDown()
            false
        }

        dataBind.inputFirstField.setOnItemClickListener { _, _, position, _ ->
            primaryOptionsValue = primaryOptions[position].value
            dismissKeyboard(dataBind.inputFirstField)
            dataBind.cardViewSecondField.visibility = View.VISIBLE
            dataBind.textNote.visibility = View.GONE
            dataBind.cardViewNote.visibility = View.GONE
            dataBind.inputSecondField.text.clear()
            dataBind.textDisplayGitCommand.text = ""
            dataBind.textDisplayNote.text = ""
            loadSecondaryOptionsFromDB(primaryOptionsValue)
        }

        dataBind.inputSecondField.setOnItemClickListener { _, _, position, _ ->
            dismissKeyboard(dataBind.inputSecondField)
            val selectedSecondaryOption = secondaryOptions[position]
            usage = selectedSecondaryOption.usage
            note = selectedSecondaryOption.nb

            dataBind.textNote.visibility = if (note.isEmpty()) View.GONE else View.VISIBLE
            dataBind.cardViewNote.visibility = if (note.isEmpty()) View.GONE else View.VISIBLE
            dataBind.textDisplayGitCommand.text = usage
            dataBind.textDisplayNote.text = note
        }
    }

    private fun loadPrimaryOptionsFromDB() {
        primaryOptions.clear()
        primaryOptions.addAll(dbHelper.getPrimaryOptions())
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            primaryOptions.map { it.label }
        )
        dataBind.inputFirstField.setAdapter(adapter)
    }

    private fun loadSecondaryOptionsFromDB(primaryValue: String) {
        secondaryOptions.clear()
        secondaryOptions.addAll(dbHelper.getSecondaryOptions(primaryValue))
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            secondaryOptions.map { it.label }
        )
        dataBind.inputSecondField.setAdapter(adapter)
    }

    private fun Context.dismissKeyboard(view: View?) {
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}