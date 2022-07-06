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
import android.widget.*
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nayan.food_runner.R
import com.nayan.food_runner.util.ConnectionManager
import com.nayan.food_runner.util.Constants
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    lateinit var etMobileNumber: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var txtForgotPassword: TextView
    lateinit var txtSignup: TextView


    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if(isLoggedIn){
            var intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        supportActionBar?.hide()
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtSignup = findViewById(R.id.txtSignUp)

        btnLogin.setOnClickListener {
            val mobileNumber = etMobileNumber.text.toString()
            val password = etPassword.text.toString()
            val queue = Volley.newRequestQueue(this@LoginActivity)
            val url = Constants.LOGIN
            var jsonparam = JSONObject()
            jsonparam.put("mobile_number", mobileNumber)
            jsonparam.put("password", password)
            if (ConnectionManager().checkConnectivity(this@LoginActivity)) {
                val jsonRequest = object: JsonObjectRequest(Request.Method.POST,url, jsonparam, Response.Listener {
                    try{
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if(success){
                            val user = data.getJSONObject("data")
                            val user_id = user.getString("user_id")
                            val name = user.getString("name")
                            val email = user.getString("email")
                            val mobile_number = user.getString("mobile_number")
                            val address = user.getString("address")

                            var bundle:Bundle = Bundle()
                            bundle.putString("id", user_id)
                            bundle.putString("name", name)
                            bundle.putString("email", email)
                            bundle.putString("mobile_number", mobile_number)
                            bundle.putString("address", address)


                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            savePreferences(bundle)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this@LoginActivity, "Some Error Occurred!!!", Toast.LENGTH_SHORT).show()
                        }
                    }catch (e: JSONException){
                        Toast.makeText(
                            this@LoginActivity,
                            "Some unexpected error occured ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        this@LoginActivity,
                        "Volley error occured ",
                        Toast.LENGTH_SHORT
                    ).show()
                }){
                    override fun getHeaders(): MutableMap<String, String> {

                        val headers = HashMap<String, String>()
                        headers["Content-Type"] = "application/json"
                        headers["token"] = "4b564f6ea2296c"
                        return headers
                    }
                }
                queue.add(jsonRequest)

            }else{
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
    fun savePreferences(bundle: Bundle){
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
        sharedPreferences.edit().putString("Bundle", bundle.toString()).apply()
    }
}