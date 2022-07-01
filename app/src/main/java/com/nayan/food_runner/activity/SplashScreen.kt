package com.nayan.food_runner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.nayan.food_runner.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        Handler().postDelayed({
            var intent = Intent(this@SplashScreen, LoginActivity::class.java)
            startActivity(intent)
        },2000)

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}