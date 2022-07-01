package com.nayan.food_runner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import com.nayan.food_runner.R

class NewRegistrationActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etMobileNumber: EditText
    lateinit var etLocation: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_registration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Register Yourself"

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etLocation = findViewById(R.id.etLocation)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val mobile = etMobileNumber.text.toString()
            val location = etLocation.text.toString()
            val pass = etPassword.text.toString()
            val confPass = etConfirmPassword.text.toString()

            if(pass != confPass){
                Toast.makeText(this@NewRegistrationActivity, "Password and Confirm Password should same", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this@NewRegistrationActivity, BlankActivity::class.java)

                val bundle = Bundle()

                bundle.putString("data", "register")
                bundle.putString("title", "Register Activity Details")
                bundle.putString("name", name)
                bundle.putString("email", email)
                bundle.putString("mobile", mobile)
                bundle.putString("location", location)
                bundle.putString("password", pass)

                intent.putExtra("details", bundle)
                startActivity(intent)
                finish()
            }
        }


    }
}