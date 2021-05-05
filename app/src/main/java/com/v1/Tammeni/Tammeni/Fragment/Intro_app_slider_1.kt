package com.v1.Tammeni.Fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.v1.Tammeni.Activities.IntroAppActivity
import com.v1.Tammeni.Helpers.MyPreference
import com.v1.Tammeni.R
import kotlinx.android.synthetic.main.activity_intro_app.*
import kotlinx.android.synthetic.main.fragment_intro_app_slider_1.*
import java.util.*


class Intro_app_slider_1 : Fragment() {

    lateinit var myPreference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_app_slider_1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val English = "en"
        val Arabic = "ar"
        myPreference = MyPreference(requireActivity().applicationContext)

        val current = resources.configuration.locale.toString()

        val green = context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
        val grey = context?.let { ContextCompat.getColor(it, R.color.colorSecondaryText) }

        if (Locale.getDefault().language == Arabic) {
            buttonArabic_intro_app.backgroundTintList = ColorStateList.valueOf(green!!)
            buttonEnglish_intro_app.backgroundTintList = ColorStateList.valueOf(grey!!)

        } else if (Locale.getDefault().language == English) {
            buttonArabic_intro_app.backgroundTintList = ColorStateList.valueOf(grey!!)
            buttonEnglish_intro_app.backgroundTintList = ColorStateList.valueOf(green!!)
        }

        if (current == Arabic) {
            buttonArabic_intro_app.backgroundTintList = ColorStateList.valueOf(green!!)
            buttonEnglish_intro_app.backgroundTintList = ColorStateList.valueOf(grey!!)

        } else if (current == English) {
            buttonArabic_intro_app.backgroundTintList = ColorStateList.valueOf(grey!!)
            buttonEnglish_intro_app.backgroundTintList = ColorStateList.valueOf(green!!)
        }

        buttonArabic_intro_app.setOnClickListener {

            myPreference.setLoginCount(Arabic)
            val langArabic: Locale = Locale.Builder().setLanguage("ar").build()
            Locale.setDefault(langArabic)

            buttonArabic_intro_app.backgroundTintList = ColorStateList.valueOf(green!!)
            buttonEnglish_intro_app.backgroundTintList = ColorStateList.valueOf(grey!!)

//            val ft: FragmentTransaction =
//                requireActivity().supportFragmentManager.beginTransaction()
//            ft.detach(this)
//            ft.attach(this)
//            ft.commit()

//            startActivity(Intent(activity, IntroAppActivity::class.java))

            requireActivity().supportFragmentManager.beginTransaction().detach(this)
                .attach(this).commit()

//            requireActivity().window.enterTransition = null

            activity?.let { it1 -> ActivityCompat.recreate(it1) }

//            requireActivity().supportFragmentManager.beginTransaction().detach(this)
//                .attach(this).commit()
//            activity?.let { it1 -> ActivityCompat.recreate(it1) }



            val intent = Intent(requireActivity(), IntroAppActivity::class.java)
            intent.putExtra("nextValue", 1)
            startActivity(intent)
    }

        buttonEnglish_intro_app.setOnClickListener {
            myPreference.setLoginCount(English)

            buttonArabic_intro_app.backgroundTintList = ColorStateList.valueOf(grey!!)
            buttonEnglish_intro_app.backgroundTintList = ColorStateList.valueOf(green!!)

//            if (grey != null) {
//                buttonEnglish_intro_app.setBackgroundColor(grey)
//
//                if (green != null) {
//                    buttonEnglish_intro_app.setBackgroundColor(green)
//                }
//            }

            requireActivity().supportFragmentManager.beginTransaction().detach(this)
                .attach(this).commit()
            activity?.let { it1 -> ActivityCompat.recreate(it1) }

            val intent = Intent(requireActivity(), IntroAppActivity::class.java)
            intent.putExtra("nextValue", 1)
            startActivity(intent)

        }
    }

}