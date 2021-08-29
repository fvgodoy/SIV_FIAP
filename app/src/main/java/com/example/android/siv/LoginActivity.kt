package com.example.android.siv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun buttonClickFunction(v: View?) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}