package com.nayan.food_runner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.*
import com.android.volley.Request
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nayan.food_runner.R
import com.nayan.food_runner.util.ConnectionManager
import com.nayan.food_runner.util.REGISTER
import com.nayan.food_runner.util.SessionManager
import com.nayan.food_runner.util.Validations
import org.json.JSONObject
import java.lang.Exception

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
        toolbar = findViewById(R.id.toolbar)

        sessionManager = SessionManager(this@NewRegistrationActivity)
        sharedPreferences = this.getSharedPreferences(sessionManager.PREF_NAME, sessionManager.PRIVATE_MODE)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(this, R.style.PoppinsTextAppearance)

        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etAddress = findViewById(R.id.etAddress)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        rlRegister = findViewById(R.id.rlRegister)
        progressBar = findViewById(R.id.progressBar)

        rlRegister.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE

        btnRegister.setOnClickListener {
            rlRegister.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            if(Validations.validateNameLength(etName.toString())){
                etName.error = null
                if(Validations.validateMobile(etMobileNumber.toString())){
                    etMobileNumber.error = null
                    if(Validations.validateEmail(etEmail.toString())){
                        etEmail.error = null
                        if(Validations.validatePasswordLength(etPassword.toString())){
                            if(Validations.matchPassword(
                                    etPassword.toString(),
                                    etConfirmPassword.toString()
                            )){
                                if(ConnectionManager().checkConnectivity(this@NewRegistrationActivity)){
                                    etPassword.error = null
                                    etConfirmPassword.error = null
                                    sendRegisterRequest(
                                        etName.toString(), etMobileNumber.toString(),
                                        etAddress.toString(), etPassword.toString(), etEmail.toString()
                                    )
                                }else{
                                    progressBar.visibility = View.INVISIBLE
                                    Toast.makeText(this@NewRegistrationActivity, "No Internet Connection", Toast.LENGTH_SHORT)
                                        .show()

                                }
                            }else{
                                progressBar.visibility = View.INVISIBLE
                                etPassword.error = "Passwords don't match"
                                etConfirmPassword.error = "Passwords don't match"
                                Toast.makeText(this@NewRegistrationActivity, "Passwords don't match", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }else{
                            rlRegister.visibility = View.VISIBLE
                            progressBar.visibility = View.INVISIBLE
                            etPassword.error = "Password should be more than or equal 4 digits"
                            Toast.makeText(
                                this@NewRegistrationActivity,
                                "Password should be more than or equal 4 digits",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }else{
                        rlRegister.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        etMobileNumber.error = "Invalid Mobile number"
                        Toast.makeText(this@NewRegistrationActivity, "Invalid Mobile number", Toast.LENGTH_SHORT).show()

                    }

                }else{
                    rlRegister.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                    etEmail.error = "Invalid Email"
                    Toast.makeText(this@NewRegistrationActivity, "Invalid Email", Toast.LENGTH_SHORT).show()
                }
            }else{
                rlRegister.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                etName.error = "Invalid Name"
                Toast.makeText(this@NewRegistrationActivity, "Invalid Name", Toast.LENGTH_SHORT).show()

            }


        }


    }
    private fun sendRegisterRequest(name: String, phone: String, address: String, password: String, email: String) {

        val queue = Volley.newRequestQueue(this)

        val jsonParams = JSONObject()
        jsonParams.put("name", name)
        jsonParams.put("mobile_number", phone)
        jsonParams.put("password", password)
        jsonParams.put("address", address)
        jsonParams.put("email", email)

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.POST,
            REGISTER,
            jsonParams,
            Response.Listener {
                try {
                    val data = it.getJSONObject("data")
                    val success = data.getBoolean("success")
                    if (success) {
                        val response = data.getJSONObject("data")
                        sharedPreferences.edit()
                            .putString("user_id", response.getString("user_id")).apply()
                        sharedPreferences.edit()
                            .putString("user_name", response.getString("name")).apply()
                        sharedPreferences.edit()
                            .putString(
                                "user_mobile_number",
                                response.getString("mobile_number")
                            )
                            .apply()
                        sharedPreferences.edit()
                            .putString("user_address", response.getString("address"))
                            .apply()
                        sharedPreferences.edit()
                            .putString("user_email", response.getString("email")).apply()
                        sessionManager.setLogin(true)
                        startActivity(
                            Intent(
                                this@NewRegistrationActivity,
                                MainActivity::class.java
                            )
                        )
                        finish()
                    } else {
                        rlRegister.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        val errorMessage = data.getString("errorMessage")
                        Toast.makeText(
                            this@NewRegistrationActivity,
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception){
                    rlRegister.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this@NewRegistrationActivity, it.message, Toast.LENGTH_SHORT).show()
                rlRegister.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"

                /*The below used token will not work, kindly use the token provided to you in the training*/
                headers["token"] = "4b564f6ea2296c"
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        Volley.newRequestQueue(this).cancelAll(this::class.java.simpleName)
        onBackPressed()
        return true
    }
}