package com.nayan.food_runner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.nayan.food_runner.R

class BlankActivity : AppCompatActivity() {
    var titleName = "Blank Activity"
    lateinit var txtData: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank)
        txtData = findViewById(R.id.txtData)

        if(intent!=null){
            val details = intent.getBundleExtra("details")

            val data = details?.getString("data")

            if (data == "login") {
                val text = "Mobile Number : ${details.getString("mobile")} \n " +
                        "Password : ${details.getString("password")}"
                txtData.text = text
                titleName = details.getString("title")!!
            }

            if (data == "register") {
                val text = " Name : ${details.getString("name")} \n " +
                        "Email : ${details.getString("email")} \n " +
                        "Mobile Number : ${details.getString("mobile")} \n " +
                        "Password : ${details.getString("password")} \n " +
                        "Address: ${details.getString("location")}"
                txtData.text = text
                titleName = details.getString("title")!!
            }

            if (data == "forgot") {
                val text = "Mobile Number : ${details.getString("mobile")} \n " +
                        "Email : ${details.getString("email")}"
                txtData.text = text
                titleName = details.getString("title")!!
            }

        }
        title = titleName



    }
}