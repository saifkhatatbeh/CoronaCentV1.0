package com.v1.Tammeni.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.v1.Tammeni.Fragment.Intro_app_slider_1
import com.v1.Tammeni.Fragment.Intro_app_slider_2
import com.v1.Tammeni.Fragment.Intro_app_slider_3
import com.v1.Tammeni.Fragment.Intro_app_slider_welcome
import com.v1.Tammeni.Helpers.MyContextWrapper
import com.v1.Tammeni.Helpers.MyPreference
import com.v1.Tammeni.R
import kotlinx.android.synthetic.main.activity_intro_app.*
import kotlinx.android.synthetic.main.custom_toast_negative.*
import java.util.*
import kotlin.collections.ArrayList


lateinit var myPreference: MyPreference

var fragment0 = Intro_app_slider_1()
var fragment1 = Intro_app_slider_2()
var fragment2 = Intro_app_slider_3()
var fragment3 = Intro_app_slider_welcome()

val pref_show_intro = "Intro"
lateinit var preference: SharedPreferences

var check = ""

class IntroAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_app)


//        setAppLocale("en")
//        myPreference.setLoginCount("en")

        preference = getSharedPreferences("IntroApp", Context.MODE_PRIVATE)

        if (!preference.getBoolean(pref_show_intro, true)) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val current = resources.configuration.locale.toString()

        val grey = getColor(R.color.colorSecondaryText)
        val white = getColor(R.color.colorWhite)

        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

        setupViewPager()

        buttonBack_intro_app.visibility = View.GONE

        view_pager_intro_app.setSwipePagingEnabled(false)

        view_pager_intro_app.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                when (view_pager_intro_app.currentItem) {

                    0 -> {
                        buttonBack_intro_app.visibility = View.GONE

                        text_intro_app.text = getString(R.string.Enter_your_preferred_language)
                        buttonBack_intro_app.text = getString(R.string.BACK)
                        buttonNext_intro_app.text = getString(R.string.NEXT)



                    }
                    1 -> {
                        buttonBack_intro_app.visibility = View.VISIBLE

                        text_intro_app.text = getString(R.string.To_report_a_bug)
                        buttonBack_intro_app.text = getString(R.string.BACK)
                        buttonNext_intro_app.text = getString(R.string.NEXT)


                        if (Build.VERSION.SDK_INT <= 26) {
                            buttonBack_intro_app.visibility = View.GONE
                        }
                        buttonNext_intro_app.isClickable = true
//                        buttonNext_intro_app.visibility = View.VISIBLE

                    }
                    2 -> {


//                      buttonNext_intro_app.isClickable = false
                        buttonBack_intro_app.visibility = View.VISIBLE
                        text_intro_app.visibility = View.VISIBLE
//                        buttonNext_intro_app.visibility = View.GONE

                        text_intro_app.text = getString(R.string.Enable_Location_services)
                        buttonBack_intro_app.text = getString(R.string.BACK)
                        buttonNext_intro_app.text = getString(R.string.NEXT)



//                        if (check == "true") {
//                            buttonNext_intro_app.visibility = View.VISIBLE
//                            buttonNext_intro_app.isClickable = true
//                        }
                    }

                    3 -> {
                        buttonBack_intro_app.visibility = View.GONE
                        text_intro_app.visibility = View.GONE

                        buttonBack_intro_app.text = getString(R.string.BACK)
                        buttonNext_intro_app.text = getString(R.string.NEXT)

                        buttonNext_intro_app.text = getString(R.string.DONE)

                        buttonNext_intro_app.setOnClickListener { toIntroSlider() }

                    }
                }
            }
        })

        if (Build.VERSION.SDK_INT <= 26) {

            view_pager_intro_app.setCurrentItem(view_pager_intro_app.currentItem + 1, true)
        }

        var langSelectorNext = intent.getIntExtra("nextValue", 0)
        if(langSelectorNext > 0)
        {
            view_pager_intro_app.setCurrentItem(view_pager_intro_app.currentItem +  1, true)

        }

    }

    fun locationCheck(p3: String?) {

        if (p3 != null) {
            check = p3
        }

    }

    fun prev(view: View?) {
        view_pager_intro_app.setCurrentItem(view_pager_intro_app.currentItem - 1, true)
    }

    fun next(view: View?) {
        view_pager_intro_app.setCurrentItem(view_pager_intro_app.currentItem + 1, true)
    }


    private fun setupViewPager() {

        val adapter = MyViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(fragment0)
        adapter.addFragment(fragment1)
        adapter.addFragment(fragment2)
        adapter.addFragment(fragment3)

        view_pager_intro_app.adapter = adapter
    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragmentList: MutableList<Fragment> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        myPreference = MyPreference(newBase!!)
        val lang = myPreference.getLoginCount()
        super.attachBaseContext(MyContextWrapper.wrap(newBase, lang!!))

    }

    private fun setAppLocale(localeCode: String) {
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        var conf: Configuration = res.configuration
        conf.locale = Locale(localeCode.toLowerCase(Locale.ROOT))
        res.updateConfiguration(conf, dm)

    }

    fun toIntroSlider() {
        startActivity(Intent(this@IntroAppActivity, IntroSliderActivity::class.java))
        finish()
        val editor = preference.edit()
        editor.putBoolean(pref_show_intro, false)
        editor.apply()
    }
}