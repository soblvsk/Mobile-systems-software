package com.bignerdranch.android.petrolprice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.petrolprice.R.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var litres: EditText
    private lateinit var petrol: RadioGroup
    private lateinit var buttonOk: Button

    var fuelPrice = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        litres = findViewById(id.countLitres)
        petrol = findViewById(id.petrol)
        buttonOk = findViewById(id.buttonOk)

        buttonOk.setOnClickListener {
            calculateCost()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun calculateCost() {
        val litersText = litres.text.toString()
        val selectedFuel = petrol.checkedRadioButtonId

        if (litersText.isEmpty() || selectedFuel == -1) {
            return
        }

        val liters = litersText.toDouble()

        fuelPrice = when (selectedFuel) {
            id.petrol92 -> 50.0
            id.petrol95 -> 55.0
            id.petrol98 -> 57.0
            else -> 0.0
        }

        val cost = liters * fuelPrice

        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("cost", cost)
        startActivity(intent)
    }
}

