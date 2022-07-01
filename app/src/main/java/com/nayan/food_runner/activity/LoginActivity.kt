package com.nayan.food_runner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.nayan.food_runner.R

class LoginActivity : AppCompatActivity() {
    lateinit var etMobileNumber: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var txtForgotPassword: TextView
    lateinit var txtSignup: TextView

    val validNumber = "0000000000"
    val validPassword = "pass"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtSignup = findViewById(R.id.txtSignUp)

        btnLogin.setOnClickListener {
            val mobileNumber = etMobileNumber.text.toString()
            val password = etPassword.text.toString()
            if(mobileNumber == validNumber && password == validPassword){
                val intent = Intent(this@LoginActivity, MainActivity::class.java)

                val bundle = Bundle()

                bundle.putString("mobile", mobileNumber)
                bundle.putString("password", password)
                bundle.putString("title", "Login Details")
                bundle.putString("data", "login")
                intent.putExtra("details", bundle)
                startActivity(intent)
            }else{
                Toast.makeText(this@LoginActivity, "Wrong Credentials", Toast.LENGTH_SHORT).show()
            }


        }

        txtForgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        txtSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, NewRegistrationActivity::class.java)
            startActivity(intent)
        }


    }
}