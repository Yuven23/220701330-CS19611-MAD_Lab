package com.example.smsalert

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etText : EditText = findViewById(R.id.etText)
        val btDisplay : Button = findViewById(R.id.btDisplay)
        btDisplay.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("MAD Lab")
                .setMessage(etText.text.toString())
                .setPositiveButton("OK") { dialog, which ->
                    Toast.makeText(applicationContext, "You clicked OK",
                        Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    Toast.makeText(applicationContext, "You clicked Cancel",
                        Toast.LENGTH_LONG).show()
                }
                .create()
            alertDialog.show()
        }
    }
}
