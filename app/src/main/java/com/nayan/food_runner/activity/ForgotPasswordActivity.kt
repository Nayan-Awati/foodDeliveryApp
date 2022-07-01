package com.nayan.food_runner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.nayan.food_runner.R

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var etMobileNumber: EditText
    lateinit var etEmail: EditText
    lateinit var btnNext: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_forgot_password_activity)

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etEmail = findViewById(R.id.etEmail)
        btnNext = findViewById(R.id.btnNext)

        btnNext.setOnClickListener {
            val mobileNumber = etMobileNumber.text.toString()
            val email = etEmail.text.toString()

            val intent = Intent(this@ForgotPasswordActivity, BlankActivity::class.java)

            val bundle = Bundle()

            bundle.putString("data", "forgot")
            bundle.putString("title", "Forgot Password Activity")
            bundle.putString("mobile", mobileNumber)
            bundle.putString("email", email)

            intent.putExtra("details", bundle)
            startActivity(intent)
            finish()

        }


    }
}