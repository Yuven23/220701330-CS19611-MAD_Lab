package com.example.simplecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.annotation.SuppressLint
import android.app.Activity // Changed import

class MainActivity : Activity() { // Inheriting from android.app.Activity
    var input1 :Double = 0.0
    var input2 :Double = 0.0
    var addition : Boolean = false
    var subtraction : Boolean = false
    var multiplication : Boolean = false
    var division : Boolean = false
    var decimal : Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvExpression : TextView = findViewById(R.id.tvExpression)
        val tvResult : TextView = findViewById(R.id.tvResult)
        val btZero : Button = findViewById(R.id.btZero)
        val btOne : Button = findViewById(R.id.btOne)
        val btTwo : Button = findViewById(R.id.btTwo)
        val btThree : Button = findViewById(R.id.btThree)
        val btFour : Button = findViewById(R.id.btFour)
        val btFive : Button = findViewById(R.id.btFive)
        val btSix : Button = findViewById(R.id.btSix)
        val btSeven : Button = findViewById(R.id.btSeven)
        val btEight : Button = findViewById(R.id.btEight)
        val btNine : Button = findViewById(R.id.btNine)
        val btAddition : Button = findViewById(R.id.btAddition)
        val btSubtraction : Button = findViewById(R.id.btSubtraction)
        val btMultiplication : Button = findViewById(R.id.btMultiplication)
        val btDivision : Button = findViewById(R.id.btDivision)
        val btDecimal : Button = findViewById(R.id.btDecimal)
        val btEqual : Button = findViewById(R.id.btEqual)
        val btClear : Button = findViewById(R.id.btClear)

        btZero.setOnClickListener {
            tvExpression.setText("${tvExpression.text}0")
        }
        btOne.setOnClickListener {
            tvExpression.setText("${tvExpression.text}1")
        }
        btTwo.setOnClickListener {
            tvExpression.setText("${tvExpression.text}2")
        }
        btThree.setOnClickListener {
            tvExpression.setText("${tvExpression.text}3")
        }
        btFour.setOnClickListener {
            tvExpression.setText("${tvExpression.text}4")
        }
        btFive.setOnClickListener {
            tvExpression.setText("${tvExpression.text}5")
        }
        btSix.setOnClickListener {
            tvExpression.setText("${tvExpression.text}6")
        }
        btSeven.setOnClickListener {
            tvExpression.setText("${tvExpression.text}7")
        }
        btEight.setOnClickListener {
            tvExpression.setText("${tvExpression.text}8")
        }
        btNine.setOnClickListener {
            tvExpression.setText("${tvExpression.text}9")
        }
        btDecimal.setOnClickListener {
            if(!decimal) {
                tvExpression.setText("${tvExpression.text}.")
                decimal = true
            }
        }
        btAddition.setOnClickListener {
            if (tvExpression.text.isNotEmpty()) {
                input1 = tvExpression.text.toString().toDouble()
                addition = true
                decimal = false
                tvExpression.text = null
            }
        }
        btSubtraction.setOnClickListener {
            if (tvExpression.text.isNotEmpty()) {
                input1 = tvExpression.text.toString().toDouble()
                subtraction = true
                decimal = false
                tvExpression.text = null
            }
        }
        btMultiplication.setOnClickListener {
            if (tvExpression.text.isNotEmpty()) {
                input1 = tvExpression.text.toString().toDouble()
                multiplication = true
                decimal = false
                tvExpression.text = null
            }
        }
        btDivision.setOnClickListener {
            if (tvExpression.text.isNotEmpty()) {
                input1 = tvExpression.text.toString().toDouble()
                division = true
                decimal = false
                tvExpression.text = null
            }
        }
        btEqual.setOnClickListener() {
            if (tvExpression.text.isNotEmpty()) {
                input2 = tvExpression.text.toString().toDouble()
                if (addition) {
                    tvExpression.setText("${input1} + ${input2}")
                    val raddition = input1 + input2
                    tvResult.setText("$raddition")
                    addition = false
                }
                if (subtraction) {
                    tvExpression.setText("${input1} - ${input2}")
                    val rsubtraction = input1 - input2
                    tvResult.setText("$rsubtraction")
                    subtraction = false
                }
                if (multiplication) {
                    tvExpression.setText("${input1} * ${input2}")
                    val rmultiplication = input1 * input2
                    tvResult.setText("$rmultiplication")
                    multiplication = false
                }
                if (division) {
                    tvExpression.setText("${input1} / ${input2}")
                    if (input2 != 0.0) {
                        val rdivision = input1 / input2
                        tvResult.setText("$rdivision")
                    } else {
                        tvResult.setText("Error")
                    }
                    division = false
                }
            }
        }
        btClear.setOnClickListener {
            tvExpression.text = ""
            tvResult.text = ""
            input1 = 0.0
            input2 = 0.0
            decimal = false
        }
    }
}