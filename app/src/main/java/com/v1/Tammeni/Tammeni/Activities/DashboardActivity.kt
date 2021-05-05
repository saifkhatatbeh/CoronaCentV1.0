package com.v1.Tammeni.Activities

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.*
import com.v1.Tammeni.Fragment.Dashboard_Fragment
import com.v1.Tammeni.Fragment.News_Fragment
import com.v1.Tammeni.Fragment.Settings_Fragment
import com.v1.Tammeni.Helpers.MyContextWrapper
import com.v1.Tammeni.Helpers.MyPreference
import com.v1.Tammeni.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    lateinit var myPreference: MyPreference

    val dashboardFragment = Dashboard_Fragment()
    val newsFragment = News_Fragment()
    val settingsFragment = Settings_Fragment()
    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null

    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = dashboardFragment

    var toolbar: Toolbar? = null

    var y = "5"
    var x = "12"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        fm.beginTransaction().add(R.id.fl_wrapper_dashbaord, settingsFragment, "3")
            .hide(settingsFragment)
            .commit()

        fm.beginTransaction().add(R.id.fl_wrapper_dashbaord, newsFragment, "2")
            .hide(newsFragment)
            .commit()

        fm.beginTransaction().add(R.id.fl_wrapper_dashbaord, dashboardFragment, "1")
            .commit()


        y = intent.getStringExtra("dash").toString()
//        y = intent.getStringExtra("dash_ar").toString()

        if (y == "dashboard") {
            fm.beginTransaction()
                .hide(active).show(dashboardFragment).commit()
            active = dashboardFragment
        }
        if (y == "news") {
            fm.beginTransaction()
                .hide(active).show(newsFragment).commit()
            active = newsFragment

            NavigationView.selectedItemId = R.id.navigationNews
        }
        if (y == "settings") {

            fm.beginTransaction().hide(active).show(settingsFragment).commit()
            active = settingsFragment

            NavigationView.selectedItemId = R.id.navigationSettings
        }

        if (y == "settings" || y == "news" || y == "dashboard") {
            x = y
        }

        if (x == "12") {
            fm.beginTransaction().hide(active).show(dashboardFragment).commit()
        }


        NavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigationDashboard -> {
                    fm.beginTransaction()
                        .hide(active).show(dashboardFragment).commit()
                    active = dashboardFragment


                }
                R.id.navigationNews -> {
                    fm.beginTransaction()
                        .hide(active).show(newsFragment).commit()
                    active = newsFragment


                }
                R.id.navigationSettings -> {
                    fm.beginTransaction()
                        .hide(active).show(settingsFragment).commit()
                    active = settingsFragment


                }
            }




            true
        }

        drawerLayout = findViewById(R.id.drawer_layout_dashboard)
        navigationView = findViewById(R.id.navigation_drawer)
        toolbar = findViewById(R.id.toolbar_dashboard)

    }


    override fun attachBaseContext(newBase: Context?) {
        myPreference = MyPreference(newBase!!)
        val lang = myPreference.getLoginCount()
        super.attachBaseContext(MyContextWrapper.wrap(newBase, lang!!))

    }

}


