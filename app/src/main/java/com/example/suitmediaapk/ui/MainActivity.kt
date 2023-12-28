package com.example.suitmediaapk.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.suitmediaapk.R
import com.example.suitmediaapk.customview.CVName

class MainActivity : AppCompatActivity() {

    private lateinit var palindromeEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        palindromeEditText = findViewById(R.id.CVPalindrome2)

        val buttoncheck = findViewById<Button>(R.id.buttonCheck)
        val button = findViewById<Button>(R.id.buttonNext)

        buttoncheck.setOnClickListener {
            performPalindromeCheck()
        }

        button.setOnClickListener {
            toScreen2()
        }
    }

    private fun toScreen2() {
        val nameUser = findViewById<CVName>(R.id.CVName)
        val extraName = nameUser.text.toString()

        val intent = Intent(this, SecondScreen::class.java).also {
            it.putExtra("NAME_USER", extraName)
            startActivity(it)
        }
    }

    private fun ifPalindrome(text: String): Boolean {
        val cleanText = text.replace("\\s".toRegex(), "").toLowerCase()
        return cleanText == cleanText.reversed()
    }

    private fun performPalindromeCheck() {
        val inputText = palindromeEditText.text.toString()
        val isPalindrome = ifPalindrome(inputText)

        val message = if (isPalindrome) {
            "Palindrome -> true"
        } else {
            "Palindrome -> false"
        }

        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}