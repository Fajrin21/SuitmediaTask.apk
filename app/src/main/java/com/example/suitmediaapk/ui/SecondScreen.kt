package com.example.suitmediaapk.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.suitmediaapk.R

class SecondScreen : AppCompatActivity() {

    private lateinit var selectedUserTextView: TextView
    private val ACCOUNT_ROW_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        val customActionBar = layoutInflater.inflate(R.layout.action_bar_layout, null)
        val params = ActionBar.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        supportActionBar?.apply {
            setCustomView(customActionBar, params)
            setDisplayShowCustomEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        selectedUserTextView = findViewById(R.id.selectedUser)

        val userName = intent.getStringExtra("NAME_USER")

        val welcome = findViewById<TextView>(R.id.userText).apply {
            text = userName
        }
        val button = findViewById<Button>(R.id.buttonChoose)
        button.setOnClickListener {
            val accountRowIntent = Intent(this@SecondScreen, AccountRow::class.java)
            startActivityForResult(accountRowIntent, ACCOUNT_ROW_REQUEST_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACCOUNT_ROW_REQUEST_CODE && resultCode == RESULT_OK) {
            // Ensure selectedUserTextView is initialized before accessing it
            if (::selectedUserTextView.isInitialized) {
                val selectedUserName = data?.getStringExtra(AccountRow.SELECTED_USER_NAME)
                selectedUserTextView.text = selectedUserName
            }
        }
    }
}