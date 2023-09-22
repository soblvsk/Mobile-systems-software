package com.bignerdranch.android.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.geoquiz.R

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val KEY_COUNTER= "counter"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var cheatCountText: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatCountText = findViewById(R.id.cheat_count)


        cheatCountText.text = "There's more: ${quizViewModel.currentCounter}"

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            if (quizViewModel.currentCounter > 0) {
                val answerIsTrue = quizViewModel.currentQuestionAnswer
                val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }
        }

        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater  =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false

            quizViewModel.cheatUsed()

            cheatButton.isEnabled = quizViewModel.currentCounter > 0
            cheatCountText.text = "There's more: ${quizViewModel.currentCounter}"
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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        savedInstanceState.putInt(KEY_COUNTER, quizViewModel.currentCounter)
        savedInstanceState.putBoolean("BTN", quizViewModel.currentQuestionEnableButton)
    }
    
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        quizViewModel.currentCounter = savedInstanceState.getInt(KEY_COUNTER)
        trueButton.isEnabled = savedInstanceState.getBoolean("BTN")
        falseButton.isEnabled = savedInstanceState.getBoolean("BTN")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)

        trueButton.isEnabled = quizViewModel.currentQuestionEnableButton
        falseButton.isEnabled = quizViewModel.currentQuestionEnableButton
        cheatButton.isEnabled = quizViewModel.currentCounter > 0
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.TOP,0,120)
            show()
        }
        quizViewModel.changEnableButton()
        trueButton.isEnabled = quizViewModel.currentQuestionEnableButton
        falseButton.isEnabled = quizViewModel.currentQuestionEnableButton
        showAnswer()

        if (userAnswer == correctAnswer) {
            quizViewModel.score += 1
        }
    }

    private fun showAnswer() {
        val questionBank = quizViewModel.questionBank
        for( (index) in questionBank.withIndex()){
            if (questionBank[index].enableButton){
                return
            }
        }

        nextButton.isEnabled = quizViewModel.currentQuestionEnableButton
        Toast.makeText(this, "You got ${quizViewModel.score} scores" +
            " out of ${questionBank.size} correct!",
            Toast.LENGTH_SHORT).show()
    }
}