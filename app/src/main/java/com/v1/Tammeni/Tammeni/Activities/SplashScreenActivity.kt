package com.v1.Tammeni.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.v1.Tammeni.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var handler: Handler

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var rotate: Animation

//        var slide_in_from_top: Animation

        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate)

        Logo.animation = rotate

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, IntroAppActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}