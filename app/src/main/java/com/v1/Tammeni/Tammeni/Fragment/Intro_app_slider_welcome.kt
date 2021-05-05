package com.v1.Tammeni.Fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.v1.Tammeni.Activities.IntroAppActivity
import com.v1.Tammeni.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.fragment_intro_app_slider_welcome.*

class Intro_app_slider_welcome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_intro_app_slider_welcome, container, false)
        var rotate: Animation

        var logo = view.findViewById<ImageView>(R.id.logo_intro_app)

//        var slide_in_from_top: Animation

        rotate = AnimationUtils.loadAnimation(view.context, R.anim.rotate_intro_app)

        logo.animation = rotate
        // Inflate the layout for this fragment
        return view
    }

}