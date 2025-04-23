package com.example.guicomponents

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Inflate the layout

        val tvText = findViewById<TextView>(R.id.tvText)
        val btFontSize = findViewById<Button>(R.id.btFontSize)
        val btFontColor = findViewById<Button>(R.id.btFontColor)
        val btBackgroundColor = findViewById<Button>(R.id.btBackgroundColor)
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

        var fontSize = 20f // Start with a reasonable font size
        var fontColorIndex = 0
        var backgroundColorIndex = 0

        btFontSize.setOnClickListener {
            tvText.textSize = fontSize
            fontSize += 5
            if (fontSize > 50) fontSize = 20f // Reset if it gets too large
        }

        btFontColor.setOnClickListener {
            when (fontColorIndex % 3) {
                0 -> tvText.setTextColor(Color.RED)
                1 -> tvText.setTextColor(Color.GREEN)
                2 -> tvText.setTextColor(Color.BLUE)
            }
            fontColorIndex++
        }

        btBackgroundColor.setOnClickListener {
            when (backgroundColorIndex % 3) {
                0 -> linearLayout.setBackgroundColor(Color.YELLOW) // Changed to a different starting color
                1 -> linearLayout.setBackgroundColor(Color.CYAN)
                2 -> linearLayout.setBackgroundColor(Color.MAGENTA) // Changed to a different color
            }
            backgroundColorIndex++
        }
    }
}