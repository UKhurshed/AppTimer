package com.ukhurshed.apptimer.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ukhurshed.apptimer.R
import java.util.*

class ScreenSplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_splash)

        Timer().schedule(object : TimerTask(){
            override fun run() {
                val intent = Intent(this@ScreenSplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1500L)

//        handler = Handler()
//        handler.postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//
//        },2000)

    }
}
