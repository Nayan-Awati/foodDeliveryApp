package com.nayan.food_runner.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nayan.food_runner.R
import com.nayan.food_runner.util.ConnectionManager
import com.nayan.food_runner.util.*
import com.nayan.food_runner.util.SessionManager
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    lateinit var etMobileNumber: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var txtForgotPassword: TextView
    lateinit var txtSignup: TextView

    lateinit var sessionManager:SessionManager
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        supportActionBar?.hide()
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtSignup = findViewById(R.id.txtSignUp)

        sessionManager = SessionManager(this)
        sharedPreferences = this.getSharedPreferences(sessionManager.PREF_NAME, sessionManager.PRIVATE_MODE)

        btnLogin.setOnClickListener {
            btnLogin.visibility = View.INVISIBLE

            val mobileNumber = etMobileNumber.text.toString()
            val password = etPassword.text.toString()

            if(Validations.validateMobile(mobileNumber) && Validations.validatePasswordLength(password)){
                if (ConnectionManager().checkConnectivity(this@LoginActivity)) {
                    val queue = Volley.newRequestQueue(this@LoginActivity)
                    val jsonParams = JSONObject()
                    jsonParams.put("mobile_number", etMobileNumber.text.toString())
                    jsonParams.put("password", etPassword.text.toString())

                    val jsonRequest = object: JsonObjectRequest(Request.Method.POST,
                        LOGIN, jsonParams, Response.Listener {
                        try{
                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            if(success){
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
                                        this@LoginActivity,
                                        MainActivity::class.java
                                    )
                                )
                                finish()
                            }
                            else{
                                Toast.makeText(this@LoginActivity, "Some Error Occurred!!!", Toast.LENGTH_SHORT).show()
                            }
                        }catch (e: JSONException){
                            e.printStackTrace()
                        }
                    }, Response.ErrorListener {
                            Log.e("Error::::", "/post request fail! Error: ${it.message}")
                        }){
                        override fun getHeaders(): MutableMap<String, String> {

                            val headers = HashMap<String, String>()
                            headers["Content-Type"] = "application/json"
                            headers["token"] = "4b564f6ea2296c"
                            return headers
                        }
                    }
                    queue.add(jsonRequest)

                }
                else{
                    val dialog = AlertDialog.Builder(this@LoginActivity)
                    dialog.setTitle("Success")
                    dialog.setMessage("Internet Connection Found")
                    dialog.setPositiveButton("Open Settings") { text, listner ->
                        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                        startActivity(intent)
                        this@LoginActivity?.finish()
                    }
                    dialog.setNegativeButton("Exit") { text, listner ->
                        ActivityCompat.finishAffinity(this@LoginActivity as Activity)
                    }
                    dialog.create()
                    dialog.show()
                }
            }else{
                Toast.makeText(this@LoginActivity, "Invalid Phone or Password", Toast.LENGTH_SHORT)
                    .show()
            }


        }


        txtForgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }

        txtSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, NewRegistrationActivity::class.java))
        }




    }

}