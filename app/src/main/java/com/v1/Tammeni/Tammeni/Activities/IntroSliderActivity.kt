package com.v1.Tammeni.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.v1.animation.*
import com.v1.Tammeni.Adapters.myPagerAdapter
import com.v1.Tammeni.R
import kotlinx.android.synthetic.main.activity_intro_slider.*


class IntroSliderActivity : AppCompatActivity() {

    var fragment1 = Intro_Slider_Fragment_1()
    var fragment2 = Intro_Slider_Fragment_2()
    var fragment3 = Intro_Slider_Fragment_3()
    var fragment4 = Intro_Slider_Fragment_4()
    var fragment5 = Intro_Slider_Fragment_5()

    lateinit var adapter: myPagerAdapter
    val pref_show_intro = "Intro"
    lateinit var preference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_slider)

        val current = resources.configuration.locale.toString()

        val green = getColor(R.color.colorPrimary)
        val grey = getColor(R.color.colorSecondaryText)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
//        var manager :FragmentManager = supportFragmentManager

        preference = getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)

        if (!preference.getBoolean(pref_show_intro, true)) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        adapter = myPagerAdapter(
            supportFragmentManager
        )

        adapter.list.add(fragment1)
        adapter.list.add(fragment2)
        adapter.list.add(fragment3)
        adapter.list.add(fragment4)
        adapter.list.add(fragment5)

        view_pager_intro_slider.adapter = adapter

        btn_next_intro_slider.setOnClickListener {

            view_pager_intro_slider.currentItem++
        }

        btn_skip.setOnClickListener {
            toLoginPage()
        }

//        if (current == "ar") {
//            indicator5_intro_slider.setTextColor(green)
//
//        } else {
        indicator1_intro_slider.setTextColor(green)
//        }


        view_pager_intro_slider.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                when (view_pager_intro_slider.currentItem) {

                    0 -> {

                        btn_next_intro_slider.text = getString(R.string.NEXT)

//                        if (current == "ar") {
//                            indicator5_intro_slider.setTextColor(green)
//
//                            indicator4_intro_slider.setTextColor(grey)

//                        } else {
                        indicator1_intro_slider.setTextColor(green)

                        indicator2_intro_slider.setTextColor(grey)

//                        }

                        btn_skip.visibility = View.VISIBLE

                        btn_next_intro_slider.setOnClickListener {

                            view_pager_intro_slider.currentItem++
                        }


                    }
                    1 -> {

                        btn_skip.visibility = View.VISIBLE
//
//                        if (current == "ar") {
//
//                            indicator3_intro_slider.setTextColor(grey)
//
//                            indicator4_intro_slider.setTextColor(green)
//
//                            indicator5_intro_slider.setTextColor(green)

//                        } else {
                        indicator2_intro_slider.setTextColor(green)

                        indicator3_intro_slider.setTextColor(grey)

//                        }

                    }
                    2 -> {

                        btn_skip.visibility = View.VISIBLE

//                        if (current == "ar") {
//                            indicator2_intro_slider.setTextColor(grey)
//
//                            indicator3_intro_slider.setTextColor(green)
//
//                            indicator4_intro_slider.setTextColor(green)
//
//                        } else {
                        indicator3_intro_slider.setTextColor(green)

                        indicator4_intro_slider.setTextColor(grey)

//                        }


                    }
                    3 -> {

                        btn_skip.visibility = View.VISIBLE

//                        if (current == "ar") {
//                            indicator1_intro_slider.setTextColor(grey)
//
//                            indicator2_intro_slider.setTextColor(green)
//
//                            indicator3_intro_slider.setTextColor(green)
//
//                        } else {
                        indicator4_intro_slider.setTextColor(green)

                        indicator5_intro_slider.setTextColor(grey)
//
//                        }

                        btn_next_intro_slider.text = getString(R.string.NEXT)

                    }
                    4 -> {

                        btn_skip.visibility = View.GONE

                        btn_next_intro_slider.text = getString(R.string.DONE)

//                        if (current == "ar") {
//                            indicator1_intro_slider.setTextColor(green)
//
//                        } else {
                        indicator5_intro_slider.setTextColor(green)

//                        }

                        btn_next_intro_slider.setOnClickListener { toLoginPage() }

                        btn_skip.setOnClickListener { toLoginPage() }
                    }
                }
            }
        })
    }

    fun toLoginPage() {
        startActivity(Intent(this@IntroSliderActivity, LoginActivity::class.java))
        finish()
        val editor = preference.edit()
        editor.putBoolean(pref_show_intro, false)
        editor.apply()
    }

}
