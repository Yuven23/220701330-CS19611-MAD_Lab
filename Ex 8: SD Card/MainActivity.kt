package com.example.sdcard

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.io.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etRegisterNumber : EditText = findViewById(R.id.etRegisterNumber)
        val etName : EditText = findViewById(R.id.etName)
        val etCGPA : EditText = findViewById(R.id.etCGPA)
        val btSave : Button = findViewById(R.id.btSave)
        val btLoad : Button = findViewById(R.id.btLoad)
        btSave.setOnClickListener {
            val registerNumber = etRegisterNumber.text.toString()
            val name = etName.text.toString()
            val cgpa = etCGPA.text.toString()
            val file = File(getExternalFilesDir(null), "student.txt")
            val outputStream = FileOutputStream(file, false)
            outputStream.write("$registerNumber,$name,$cgpa\n".toByteArray())
            outputStream.close()
            etRegisterNumber.text.clear()
            etName.text.clear()
            etCGPA.text.clear()
        }
        btLoad.setOnClickListener {
            val file = File(getExternalFilesDir(null), "student.txt")
            val inputStream = FileInputStream(file)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String
            line = bufferedReader.readLine()
            val parts = line.split(",")
            etRegisterNumber.setText(parts[0])
            etName.setText(parts[1])
            etCGPA.setText(parts[2])
            inputStream.close()
        }
    }
}