package com.bignerdranch.android.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.geoquiz.R

private const val TAG = "CheatActivity"
private const val KEY="AnswerShowned"
private const val ANSWER_KEY = "Answer"

private const val EXTRA_ANSWER_IS_TRUE =
    "com.bignerdranch.android.geoquiz.answer_is_true"

const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var versionText: TextView

    private var answerIsTrue = false
    private var shownAnswer = false



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        versionText = findViewById(R.id.api_level_android)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        shownAnswer = savedInstanceState?.getBoolean(KEY, false) ?: false
        answerTextView.text = savedInstanceState?.getString(ANSWER_KEY, "")
        setAnswerShownResult(shownAnswer)

        versionText.text = "API Level ${android.os.Build.VERSION.SDK_INT}"

        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }

            answerTextView.setText(answerText)
            shownAnswer = true
            setAnswerShownResult(shownAnswer)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.run {
            putBoolean(KEY, shownAnswer)
            putString(ANSWER_KEY, answerTextView.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}