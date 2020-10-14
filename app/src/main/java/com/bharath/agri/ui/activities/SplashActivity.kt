package com.bharath.agri.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bharath.agri.R
import com.bharath.agri.constants.Constants

class SplashActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }, 1500)

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}

