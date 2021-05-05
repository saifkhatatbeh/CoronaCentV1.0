package com.v1.Tammeni.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Px
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bugsee.library.send.SendBundleActivity.getIntent
import com.v1.Tammeni.Activities.*
import com.v1.Tammeni.Adapters.LiveNewsAdapter
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.Helpers.MyPreference
import com.v1.Tammeni.R
import com.v1.Tammeni.data.CoronaData
import com.v1.Tammeni.data.News.News
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class Dashboard_Fragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mAuth: FirebaseAuth
    lateinit var leaderBoardList: MutableList<String>
    lateinit var mDatabase: DatabaseReference
    lateinit var fragment1: BlankFragment
    lateinit var fragment2: BlankFragment2
    lateinit var dialog_network: Dialog
    private val milliSecondsPerInch = 5000f
    var a = ""
    lateinit var adapter: liveDataAdapter
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var LinearLayout: LinearLayoutManager
    private var PERMISSION_ID = 1000
    lateinit var recyclerView: RecyclerView
    lateinit var dialog: Dialog
    var population = 0.00
    var total_cases = 0.00
    var new_deaths = 0.00
    var daily_cases = 0.00
    var recovered = 0.00
    var active_cases = 0.00
    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var toolbar: Toolbar? = null
    var horizontalLayoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    var xy = "connected"
    lateinit var myPreference: MyPreference


//   Global Initializations -----------------------------------------------------------------------------------------------------------

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return view
    }

//    End of Create View------------------------------------------------------------------------------------------------------------------

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance().getReference("UserRanks")

        val green = context?.let { getColor(it, R.color.colorPrimary) }
        val grey = context?.let { getColor(it, R.color.colorSecondaryText) }

        val config: Configuration = requireContext().resources.configuration

        if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            //RTL
            indicator_1.setTextColor(grey!!)
            indicator_2.setTextColor(green!!)
        } else {
            indicator_1.setTextColor(green!!)
            indicator_2.setTextColor(grey!!)
        }

        mAuth = FirebaseAuth.getInstance()
        fusedLocationProviderClient = activity?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!

        cardTestNow.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
        cardCountries.setCardBackgroundColor(resources.getColor(R.color.colorWhite))
        cardLeaderboard.setCardBackgroundColor(resources.getColor(R.color.colorWhite))

        isRefresh()
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
        view_pager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                @Px positionOffsetPixels: Int
            ) {
                when (view_pager.currentItem) {

                    0 -> {

                        if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                            //RTL
                            indicator_1.setTextColor(grey!!)
                            indicator_2.setTextColor(green!!)
                        } else {
                            indicator_1.setTextColor(green!!)
                            indicator_2.setTextColor(grey!!)
                        }

                    }
                    1 -> {

                        if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                            //RTL
                            indicator_1.setTextColor(green!!)
                            indicator_2.setTextColor(grey!!)
                        } else {
                            indicator_1.setTextColor(grey!!)
                            indicator_2.setTextColor(green!!)
                        }

                    }
                }
            }

            override fun onPageSelected(position: Int) {

                when (view_pager.currentItem) {

                    0 -> {


                        if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                            //RTL
                            indicator_1.setTextColor(green!!)
                            indicator_2.setTextColor(grey!!)
                        } else {
                            indicator_1.setTextColor(grey!!)
                            indicator_2.setTextColor(green!!)
                        }

                        if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                            //RTL
                            indicator_1.setOnClickListener {
                                view_pager.currentItem++
                            }
                        } else {
                            indicator_2.setOnClickListener {
                                view_pager.currentItem++
                            }
                        }

                    }

                    1 -> {

                        if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                            //RTL
                            indicator_1.setTextColor(grey!!)
                            indicator_2.setTextColor(green!!)
                        } else {
                            indicator_1.setTextColor(green!!)
                            indicator_2.setTextColor(grey!!)
                        }


                        if (config.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                            //RTL
                            indicator_2.setOnClickListener {
                                view_pager.currentItem--
                            }
                        } else {
                            indicator_1.setOnClickListener {
                                view_pager.currentItem--
                            }
                        }

                    }

                }
            }

        })

//----------------------------------------------------------------------------------------------------------------------------------------------------------------
//      Connection:
        dialog_network = Dialog(requireActivity())
        dialog = activity?.let { Dialog(it) }!!
        dialog.setContentView(R.layout.full_screen_progress_bar)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        if (xy == "connected") {

            val nConnection = NetworkConnection(requireActivity().applicationContext)

            nConnection.observe(requireActivity(), androidx.lifecycle.Observer { isConnected ->
                if (isConnected) {
                    getLastLocation()
                    fetchJson()
                    fetchNewsJson()


                } else {
                    dialog.dismiss()
                    val customToast_negative: View =
                        layoutInflater.inflate(
                            R.layout.custom_toast_negative,
                            fl_wrapper_fill_all_fields_toast
                        )
                    Toast(activity).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.No_Internet_Connection)
                        view = customToast_negative
                    }.show()

                }
            })

        }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------
//      TimeLine Time:
        val current = resources.configuration.locale.toString()

        if (current.contains("ar") || Locale.getDefault().language == "ar") {


            Locale.setDefault(resources.configuration.locale)
            val c = Calendar.getInstance().time
            val df =
                SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
            val formattedDate: String = df.format(c)
            dateTimeLine.text = formattedDate

        } else {
            val c = Calendar.getInstance().time
            val df =
                SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
            val formattedDate: String = df.format(c)
            dateTimeLine.text = formattedDate
        }
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------

        menu() //Calling the menu

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
//      Initializing Recycler views:

        recyclerView = recycler_view_news
        recycler_view_news.setHasFixedSize(true)
        horizontalLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_news?.layoutManager = horizontalLayoutManager

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
//      Adding click listeners to the card views
        cardTestNow.setOnClickListener {

            Handler().postDelayed({
                cardTestNow.setCardBackgroundColor(resources.getColor(R.color.colorWhite))

            }, 500)


            startActivity(
                Intent(
                    activity,
                    TestNowActivity::class.java
                )
            )

            cardTestNow.setCardBackgroundColor(resources.getColor(R.color.colorWhite2))

        }

        cardCountries.setOnClickListener {

            Handler().postDelayed({
                cardCountries.setCardBackgroundColor(resources.getColor(R.color.colorWhite))

            }, 500)

            startActivity(
                Intent(
                    activity,
                    CountriesActivity::class.java
                )
            )

            cardCountries.setCardBackgroundColor(resources.getColor(R.color.colorWhite2))

        }

        cardLeaderboard.setOnClickListener {

            Handler().postDelayed({
                cardLeaderboard.setCardBackgroundColor(resources.getColor(R.color.colorWhite))

            }, 500)

            startActivity(
                Intent(
                    activity,
                    LeaderboardActivity::class.java
                )
            )
            cardLeaderboard.setCardBackgroundColor(resources.getColor(R.color.colorWhite2))

        }

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }
//    End of onCreateActivity------------------------------------------------------------------------------------------------------------------------------------------
//    Beginning of the functions------------------------------------------------------------------------------------------------------------------------------------------

    private fun menu() {

        mAuth = FirebaseAuth.getInstance()

        invalidateOptionsMenu(activity);

        drawerLayout = drawer_layout_dashboard
        navigationView = navigation_drawer
//        textView=findViewById(R.id.textView);
        toolbar = toolbar_dashboard

        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)

        var toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout?.addDrawerListener(toggle)

        toggle.syncState()

        navigationView?.bringToFront()

        navigationView?.setNavigationItemSelectedListener(this)

        navigationView?.setCheckedItem(R.id.nav_dashboard)

    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {
            R.id.nav_dashboard -> {
                navigationView?.setCheckedItem(R.id.nav_dashboard)
            }
            R.id.nav_contact_us -> {
                startActivity(Intent(activity, ContactUsActivity::class.java))
                activity?.finish()
            }
            R.id.nav_logout -> {
                mAuth.signOut()
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
            R.id.nav_rate_us -> {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + "${activity?.packageName}")
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=${activity?.packageName}")
                        )
                    )
                    activity?.finish()

                }
            }
        }
        drawerLayout?.closeDrawer(GravityCompat.START)
        return true

    }

    private fun isRefresh() {

        SwipeRefresh.setOnRefreshListener {

            activity?.applicationContext?.let { NetworkConnection(it) }
                ?.observe(requireActivity(), androidx.lifecycle.Observer { isConnected ->

                    if (isConnected) {


                        getLastLocation()
                        fetchJson()
                        fetchNewsJson()

//                        requireActivity().finish()
//                        requireActivity().overridePendingTransition(0, 0)
//                        requireActivity().startActivity(requireActivity().intent)
//                        requireActivity().overridePendingTransition(0, 0)

                    } else {

                        dialog.dismiss()

                        val customToast_negative: View =
                            layoutInflater.inflate(
                                R.layout.custom_toast_negative,
                                fl_wrapper_fill_all_fields_toast
                            )
                        Toast(activity).apply {
                            customToast_negative.toast_text.text =
                                getString(R.string.No_Internet_Connection)
                            view = customToast_negative
                        }.show()

                        SwipeRefresh.setRefreshing(false)


                    }

                })

        }

    }

    //    Location Functions----------------------------------------------------------------------------------------------------------------------------------------------------
    private fun getLastLocation() {

        val current = resources.configuration.locale.toString()

        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

        val customToast_positive: View =
            layoutInflater.inflate(R.layout.custom_toast_positive, fl_wrapper_fill_all_fields_toast)

        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener() { task ->
                    var location = task.result
                    if (location == null) {
                        getNewLocation()
                    } else {
                        a = getCountryName(location.latitude, location.longitude)

                        fetchJson()

                    }
                }

            } else {

                Toast(activity).apply {
                    customToast_negative.toast_text.text =
                        getString(R.string.Please_Enable_Location_Services)
                    view = customToast_negative
                }.show()

            }

        } else {
            requestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.numUpdates = 2
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation = p0.lastLocation
            a = getCountryName(lastLocation.latitude, lastLocation.longitude)
            fetchJson()
        }
    }

    private fun checkPermission(): Boolean {

        if (
            activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED ||
            activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }

    private fun requestPermission() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), PERMISSION_ID
            )
        }
    }

    private fun isLocationEnabled(): Boolean {

        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }

    private fun getCountryName(lat: Double, long: Double): String {
        var geoCoder = Geocoder(activity, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        a = address.get(0).countryName
        return a
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "You Have Permission")
            }
        }
    }

//  End of Location Functions-------------------------------------------------------------------------------------------------------------------------------------------------

//  Json Functions----------------------------------------------------------------------------------------------------------------------------------------------------------

    fun fetchJson() {

        val current = resources.configuration.locale.toString()

        leaderBoardList = mutableListOf()

        var adapter = liveDataAdapter(super.getChildFragmentManager())
        val url = "https://corona.lmao.ninja/v2/countries?yesterday&sort"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val CoronD = gson.fromJson(body, CoronaData::class.java)

                activity?.runOnUiThread {
                    var index = -1
                    for (i in CoronD.indices) {
                        var tester = CoronD.get(i).countryInfo.iso2

                        val countryDefault: Locale = Locale.Builder().setRegion(tester).build()

                        var currentLanguage: String = Locale.getDefault().displayLanguage

                        var langSelector: Locale = Locale.Builder().setLanguage("en").build()

                        val current = resources.configuration.locale.toString()

                        if (current == "ar") {
                            langSelector = Locale.Builder().setLanguage("ar").build()
                        }

                        if (currentLanguage.toLowerCase(Locale.ROOT).contains("العربية")) {
                            langSelector = Locale.Builder().setLanguage("ar").build()

                        }
                        if (countryDefault.getDisplayCountry(langSelector) == a || CoronD.get(i).country == a) {
                            index = i
                            break
                        }
                    }

                    var corona = CoronD[index]

                    mAuth = FirebaseAuth.getInstance()

                    dialog.dismiss()

                    val user = mAuth.currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
                        val name = user.displayName
                        navigationView = navigation_drawer as NavigationView
                        val headerView = navigationView!!.getHeaderView(0)
                        val img: ImageView =
                            headerView.findViewById(R.id.photoUser) as ImageView
                        val navUsername: TextView =
                            headerView.findViewById<View>(R.id.headerName) as TextView

                        val navRank: TextView =
                            headerView.findViewById<View>(R.id.header_rank) as TextView

                        val rankCardView: CardView =
                            headerView.findViewById(R.id.cardview_rank_header) as CardView

                        if (it.isAnonymous) {
                            rankCardView.visibility = View.GONE
                        }
                        navUsername.setText(name)

                        val zone = mDatabase.child(user.uid)

                        zone.addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {

                                if (snapshot!!.exists()) {
                                    leaderBoardList.clear()
                                    for (h in snapshot.children) {
                                        val leaderboard = h.getValue()
                                        leaderBoardList.add(leaderboard.toString())
                                    }
                                    var rank = leaderBoardList.get(2)

                                    navRank.setText(rank)

                                }
                            }

                        })

                        val email = user?.email
                        if (email != null) {
                            if (email.isNullOrBlank()) {

                            } else {
                            }
                        }
                        val photoUrl = user.photoUrl
                        Picasso.get().load(corona.countryInfo.flag).into(img)

                        val emailVerified = user.isEmailVerified
                    }

                    if (a == "") {
                        val customToast_negative: View =
                            layoutInflater.inflate(
                                R.layout.custom_toast_negative,
                                fl_wrapper_fill_all_fields_toast
                            )

                        Toast(activity).apply {
                            customToast_negative.toast_text.text =
                                getString(R.string.Enable_location_and_restart_your_app)
                            view = customToast_negative
                        }.show()


                    }

                    population = corona.population.toDouble() / 100.00
                    total_cases = corona.cases.toDouble() / 100.00
                    new_deaths = corona.todayDeaths.toDouble() / 100.00
                    daily_cases = corona.todayCases.toDouble() / 100.00
                    recovered = corona.recovered.toDouble() / 100.00
                    active_cases = corona.active.toDouble() / 100.00


                    val rounderAverage_population =
                        population.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()

                    val rounderAverage_total_cases =
                        total_cases.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()

                    val rounderAverage_daily_cases =
                        daily_cases.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()

                    val rounderAverage_new_deaths =
                        new_deaths.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()

                    val rounderAverage_recovered =
                        recovered.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()

                    val rounderAverage_active_cases =
                        active_cases.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()


                    fragment1 = BlankFragment.newInstance(
                        rounderAverage(rounderAverage_active_cases),
                        rounderAverage(rounderAverage_daily_cases),
                        rounderAverage(rounderAverage_new_deaths)
                    )
                    fragment2 = BlankFragment2.newInstance(
                        rounderAverage(rounderAverage_total_cases),
                        rounderAverage(rounderAverage_recovered),
                        rounderAverage(rounderAverage_population)
                    )


                    adapter.list.add(fragment1)
                    adapter.list.add(fragment2)

                    view_pager.adapter = adapter


                }

            }


            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }


        })

    }

    fun fetchNewsJson() {
        val url = "https://coronavirus-smartable.p.rapidapi.com/news/v1/global/"

        val request = Request.Builder().url(url)
            .header("x-rapidapi-host", "coronavirus-smartable.p.rapidapi.com")
            .header("x-rapidapi-key", "123cdd7f54msh4bc26e5c43d8fa0p1bcfb1jsn114d28b1cf60")
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()

                    val News = gson.fromJson(body, News::class.java)


                    activity?.runOnUiThread {

                       recyclerView.adapter = LiveNewsAdapter(News)
                        val linearSmoothScroller: LinearSmoothScroller =
                            object : LinearSmoothScroller(activity) {
                                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                                    return milliSecondsPerInch / displayMetrics.densityDpi
                                }
                            }
                        linearSmoothScroller.targetPosition =
                            19 //the last position of the item in the list
                        GlobalScope.launch(context = Dispatchers.Main) {

                            delay(100)
                            horizontalLayoutManager.startSmoothScroll(linearSmoothScroller)
                            horizontalLayoutManager.isSmoothScrollbarEnabled
                            horizontalLayoutManager.isSmoothScrolling

                            //linearSmoothScroller.targetPosition = 0

                        }

                        SwipeRefresh.setRefreshing(false)


                    }
                } else {
                    activity?.runOnUiThread {
                        text_no_news_dashboard.visibility = View.VISIBLE
                        SwipeRefresh.setRefreshing(false)

                    }
                }

            }

            override fun onFailure(call: Call, e: IOException) {

                activity?.runOnUiThread {
                    text_no_news_dashboard.visibility = View.VISIBLE
                    SwipeRefresh.setRefreshing(false)

                }
            }

        })


    }

//   End of Json Functions--------------------------------------------------------------------------------------------------------------------------------------------------

    private fun rounderAverage(a2: Double): String {

        var decimal = ""

        if (a2 >= 10000000) {

            decimal = (a2 / 10000000.00).toBigDecimal()
                .setScale(2, RoundingMode.UP)
                .toDouble().toString() + "B"
            return decimal

        } else if (a2 >= 10000) {
            decimal =
                (a2 / 10000.00).toBigDecimal().setScale(2, RoundingMode.UP)
                    .toDouble()
                    .toString() + "M"

            return decimal

        } else if (a2 >= 10) {

            decimal =
                (a2 / 10.00).toBigDecimal().setScale(2, RoundingMode.UP)
                    .toDouble()
                    .toString() + "K"
            return decimal


        } else {
            decimal = (a2 * 100.0).toInt().toString()

            return decimal
        }

    }

// End of functions

}

// End of the class

