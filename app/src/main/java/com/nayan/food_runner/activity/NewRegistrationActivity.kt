package com.nayan.food_runner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.widget.*
import com.nayan.food_runner.R
import com.nayan.food_runner.util.SessionManager

class NewRegistrationActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etMobileNumber: EditText
    lateinit var etAddress: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button
    lateinit var rlRegister: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_registration)

        sessionManager = SessionManager(this@NewRegistrationActivity)
        sharedPreferences = this.getSharedPreferences(sessionManager.PREF_NAME, sessionManager.PRIVATE_MODE)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar = findViewById(R.id.toolbar)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etAddress = findViewById(R.id.etAddress)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        rlRegister = findViewById(R.id.rlRegister)
        progressBar = findViewById(R.id.progressBar)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val mobile = etMobileNumber.text.toString()
            val location = etAddress.text.toString()
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