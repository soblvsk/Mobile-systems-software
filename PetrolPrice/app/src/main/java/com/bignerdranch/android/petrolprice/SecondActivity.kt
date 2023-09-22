package com.bignerdranch.android.petrolprice

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.petrolprice.R.*


class SecondActivity: AppCompatActivity() {

    private lateinit var textCost: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_second)

        textCost = findViewById(id.cost)

        val cost = intent.getDoubleExtra("cost", 0.0)
        textCost.text = "Стоимость = $cost руб."
    }
}