package com.example.basiccalculator

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.basiccalculator.ui.theme.BasicCalculatorTheme

class MainActivity : Activity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var input1 = findViewById<EditText>(R.id.input1)
        var input2 = findViewById<EditText>(R.id.input2)
        var result = findViewById<EditText>(R.id.output)

        var add = findViewById<Button>(R.id.addbutton)
        var sub = findViewById<Button>(R.id.subbutton)
        var mul = findViewById<Button>(R.id.mulbutton)
        var div = findViewById<Button>(R.id.divbutton)
        var mod = findViewById<Button>(R.id.modbutton)

        add.setOnClickListener{
            var a:String = input1.text.toString()
            var b:String = input2.text.toString()
            result.setText((a.toInt()+b.toInt()).toString())
            Toast.makeText(this,"Result Displayed!",Toast.LENGTH_SHORT).show()
        }
        sub.setOnClickListener{
            var a:String = input1.text.toString()
            var b:String = input2.text.toString()
            result.setText((a.toInt()-b.toInt()).toString())
            Toast.makeText(this,"Result Displayed!",Toast.LENGTH_SHORT).show()
        }
        mul.setOnClickListener{
            var a:String = input1.text.toString()
            var b:String = input2.text.toString()
            result.setText((a.toInt()*b.toInt()).toString())
            Toast.makeText(this,"Result Displayed!",Toast.LENGTH_SHORT).show()
        }
        div.setOnClickListener{
            var a:String = input1.text.toString()
            var b:String = input2.text.toString()
            result.setText((a.toInt()/b.toInt()).toString())
            Toast.makeText(this,"Result Displayed!",Toast.LENGTH_SHORT).show()
        }
        mod.setOnClickListener{
            var a:String = input1.text.toString()
            var b:String = input2.text.toString()
            result.setText((a.toInt()%b.toInt()).toString())
            Toast.makeText(this,"Result Displayed!",Toast.LENGTH_SHORT).show()
        }
    }
}
