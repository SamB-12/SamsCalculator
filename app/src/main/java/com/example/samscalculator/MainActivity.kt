package com.example.samscalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView ?= null
    private var isLastNumeric:Boolean = false
    private var isLastDot:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun btnClicked(view:View){
        tvInput?.append((view as Button).text)
        isLastNumeric = true
    }

    fun onClear(view: View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view : View){
        if(isLastNumeric && !isLastDot){
            tvInput?.append(".")
            isLastDot = true
            isLastNumeric = false
        }

    }

    fun onOperator(view: View){
        tvInput?.text?.let {
            if(isLastNumeric && !onOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                isLastNumeric = false
                isLastNumeric = false
            }
        }

    }


    fun onEquals(view: View){
        if(isLastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix:String = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    var splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if(tvValue.contains("+")){
                    var splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else if(tvValue.contains("*")){
                    var splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text =removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }else if(tvValue.contains("/")){
                    var splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    try {
                        tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    } catch (e: ArithmeticException){
                        e.printStackTrace()
                    }


                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result:String):String{
        var value = result
        if(value.contains(".0")){
            value = result.substring(0,result.length-2)
        }

        return value
    }

    private fun onOperatorAdded(value : String):Boolean {
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")||
                    value.contains("*")||
                    value.contains("+")||
                    value.contains("-")
        }
    }




}