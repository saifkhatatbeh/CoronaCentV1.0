package com.v1.Tammeni.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.Fragment.*
import com.v1.Tammeni.R
import com.v1.Tammeni.data.AvgDate
import com.v1.Tammeni.data.CoronaData
import com.v1.Tammeni.data.UserData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.gson.GsonBuilder
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_test_now.*
import kotlinx.android.synthetic.main.alert_quit_testnow_dialogue.*
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.exp

class TestNowActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var mFirestore: FirebaseFirestore

    var page = 0
    var age = ""
    var c = 0

    lateinit var radio_Group: RadioGroup
    lateinit var calculate_Button: RadioButton
    lateinit var gender_Choice: TextView
    lateinit var countryCodePicker: CountryCodePicker
    lateinit var dialog: Dialog
    lateinit var functions: FirebaseFunctions
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private var PERMISSION_ID = 1000

    var fragment0 = Testnow_fragment_0_countrypicker()
    var fragment1 = Testnow_fragment_1()
    var fragment2 = Testnow_fragment_2()
    var fragment3 = Testnow_fragment_3()
    var page_0 = ""
    var page_1 = ""
    var page_2 = ""
    var page_3 = ""
    var encounter = ""
    var aa = ""
    var smoking_coef = 0.0
    var gender_coef = 0.0
    var age_coef = 0.0203
    var age_multi = 0.0
    var alcohol_coef = 0.0
    var washing_coef = 0.0
    var handSanitizer_coef = 0.0
    var mask_coef = 0.0
    var encounter_coef = 0.00953
    var encounter_multi = 0.0
    var a = 0.0

    var asthma_coef = 0.0
    var kidney_coef = 0.0
    var liver_coef = 0.0
    var immune_coef = 0.0
    var heart_coef = 0.0
    var lung_coef = 0.0
    var diabetes_coef = 0.0
    var hiv_coef = 0.0

    var contactsCovid_coef = 1.389
    var contactsCovid_multi = 0.0
    var nursing_coef = 0.0
    var healthWorker_coef = 0.0

    var contacts_Covid = ""
    var b = 0.0

    var tester = 0.0
    var tester1 = 0.0
    var tester2 = 0.0
    var tester3 = 0.0
    var tester4 = 0.0
    var tester5 = 0.0
    var AverageCases = 0.0
    var gender = ""
    var hand_sanitizer = ""
    var mask = ""
    var smoking = ""
    var washing = ""
    var alcohol = ""
    var region = ""
    var diagnosedWithCovid = ""
    var asthma = ""

    var kidney = ""

    var liver = ""

    var immune = ""

    var heart = ""

    var lung = ""

    var diabetes = ""

    var hypertension = ""

    var hiv = ""
    var iso = ""
    var fever = ""
    var cough = ""
    var tiredness = ""
    var breathing_diff = ""
    var chest_pain = ""
    var loss_in_speach = ""
    var nursing_home = ""
    var health_worker = ""

    var covid19Symptoms = 0.0

    var covidBoolean = false

    var weight = 0

    var feverWeight = 0
    var coughWeight = 0
    var tirednessWeight = 0
    var breathingDiffWeight = 0
    var chestPainWeight = 0
    var lossInSpeachWeight = 0
    var total = 0.0

    var active: Fragment = fragment1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_now)
        val current = resources.configuration.locale.toString()

        val countryList = arrayOf("Jordan", "UK", "France", "Germany", "Canada")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countryList)
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        mFirestore = FirebaseFirestore.getInstance()
        functions = FirebaseFunctions.getInstance()

        val nConnection = NetworkConnection(this)
        nConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
            if (!isConnected) {
                val customToast_negative: View =
                    layoutInflater.inflate(
                        R.layout.custom_toast_negative,
                        fl_wrapper_fill_all_fields_toast
                    )

                Toast(this).apply {
                    customToast_negative.toast_text.text =
                        getString(R.string.No_Internet_Connection)
                    view = customToast_negative
                }.show()

                finish()
            }
        })


//        countryCodePicker = findViewById(R.id.country_picker)

//        spinnerResult.text = countryCodePicker.selectedCountryName
//
//        countryCodePicker.setOnCountryChangeListener {
//            spinnerResult.text = countryCodePicker.selectedCountryName
//        }


//        fm.beginTransaction().add(R.id.settings, settingsFragment, "3").hide(settingsFragment).commit();
//        fm.beginTransaction().add(R.id.news, newsFragment, "2").hide(newsFragment).commit();
//        fm.beginTransaction().add(R.id.main_container,dashboardFragment, "1").commit();

//        spinnerCountry.adapter = arrayAdapter
//
//
//        spinnerCountry.onItemSelectedListener = object :
//
//            AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                spinnerResult.text = countryList[p2]
//            }
//
//        }
        fusedLocationProviderClient = this?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!

        getLastLocation()

        setupViewPager()
        pageChange()

        buttonBack.visibility = View.GONE


        view_pager_test_now.setSwipePagingEnabled(false)

//            val progressDialog = ProgressDialog(this)
//            var a = false
//            progressDialog.setMessage("Loading...")
//            progressDialog.setCancelable(false)
//            progressDialog.show()
//            Handler().postDelayed({ progressDialog.dismiss() }, 5000)


        backArrow.setOnClickListener {
            finish()
        }

//        radio_Group = findViewById(R.id.radioGroup)
//        gender_Choice = findViewById(R.id.gender_choice)
//        calculate_Button = findViewById(R.id.buttonCalculate)

//        var radiovalue = ""
//        radioGroup_gender.setOnCheckedChangeListener { _, checkedId ->
//            val radio: RadioButton = findViewById(checkedId)
//
//            radiovalue = radio.text.toString()
//
//            Toast.makeText(this, "On Click $radiovalue", Toast.LENGTH_SHORT).show()
//
//        }

//        buttonNext.setOnClickListener {
//
//            Toast.makeText(this, "On Button Click $radiovalue", Toast.LENGTH_SHORT).show()
//
//            startActivity(Intent(this, TestResultActivity::class.java))
//
//        }
    }

    fun co(p0: String?, p1: String?, p2: String?, p3: String?) {

        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

        val customToast_positive: View =
            layoutInflater.inflate(R.layout.custom_toast_positive, fl_wrapper_fill_all_fields_toast)

        val current = resources.configuration.locale.toString()


        if (p1 == null && p2 == null && p3 == null) {
            if (p0 != null) {
                page_0 = p0
            }

        } else if (p0 == null && p2 == null && p3 == null) {
            if (p1 != null) {
                page_1 = p1
            }

        } else if (p0 == null && p1 == null && p3 == null) {
            if (p2 != null) {
                page_2 = p2
            }

        } else if (p0 == null && p1 == null && p2 == null) {
            if (p3 != null) {
                page_3 = p3
            }

        }


        val delim = "/"

        val arr0 = page_0.split(delim).toTypedArray()
        if (arr0.size > 1) {
            region = arr0.get(0)
            diagnosedWithCovid = arr0.get(1)
            if (diagnosedWithCovid == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_COVID_Diagnoses_Field)
                        view = customToast_negative
                    }.show()

                }
            } else {
                buttonNext.setOnClickListener() {
                    view_pager_test_now.setCurrentItem(view_pager_test_now.currentItem + 1, true)

                }
            }
        }

        val arr = page_1?.split(delim)?.toTypedArray()

        if (arr.size > 1) {
            gender = arr.get(0)
            hand_sanitizer = arr.get(1)
            mask = arr.get(2)
            smoking = arr.get(3)
            washing = arr.get(4)
            alcohol = arr.get(5)

            if (arr.get(6).isEmpty()) {
                age = arr.get(6)
            } else {
                c = arr.get(6).toInt()
            }

            if (arr.get(7).isEmpty()) {
                encounter = arr.get(7)
            } else {
                a = arr.get(7).toDouble()
            }


            if (smoking == "Never" || smoking == "Quit more than 10 years" || smoking == "Quit more than 5 years" || smoking == "Quit recently" || smoking == "مطلقا" || smoking == "مُقلع عن التدخين منذ أكثر من 10 سنوات" || smoking == "مُقلع عن التدخين منذ أكثر من 5 سنوات" || smoking == "مقلع عن التدخين مؤخراً") {
                smoking_coef = 0.0
            } else if (smoking == "Light smoker" || smoking == "مدخن خفيف") {
                smoking_coef = 0.198
            } else if (smoking == "Medium smoker" || smoking == "مدخّن باعتدال") {
                smoking_coef = 0.198 * 2
            } else if (smoking == "Heavy smoker" || smoking == "مدخّن بكثرة") {
                smoking_coef = 0.198 * 3
            }

            if (gender == "Female" || gender == "أنثى") {
                gender_coef = 0.0
            } else {
                gender_coef = 0.624
            }

            encounter_multi = encounter_coef * a

            if (hand_sanitizer == "Never" || hand_sanitizer == "مطلقا") {
                handSanitizer_coef = -0.348 * -2
            } else if (hand_sanitizer == "Rarely" || hand_sanitizer == "نادرا") {
                handSanitizer_coef = -0.348 * -1
            } else if (hand_sanitizer == "Sometimes" || hand_sanitizer == "أحيانا") {
                handSanitizer_coef = -0.348 * 0
            } else if (hand_sanitizer == "Often" || hand_sanitizer == "غالبا") {
                handSanitizer_coef = -0.348 * 1

            } else if (hand_sanitizer == "Always" || hand_sanitizer == "دائما") {
                handSanitizer_coef = -0.348 * 2
            }

            if (mask == "Never" || mask == "مطلقا") {
                mask_coef = 0.0
            } else if (mask == "Rarely" || mask == "نادرا") {
                mask_coef = 2 * -0.0919
            } else if (mask == "Sometimes" || mask == "أحيانا") {
                mask_coef = 3 * -0.0919
            } else if (mask == "Often" || mask == "غالبا") {
                mask_coef = 4 * -0.0919
            } else if (mask == "Always" || mask == "دائما") {
                mask_coef = 5 * -0.0919
            }

            if (mask == "Never" || mask == "مطلقا") {
                mask_coef = 0.0
            } else if (mask == "Rarely" || mask == "نادرا") {
                mask_coef = 2 * -0.0919
            } else if (mask == "Sometimes" || mask == "أحيانا") {
                mask_coef = 3 * -0.0919
            } else if (mask == "Often" || mask == "غالبا") {
                mask_coef = 4 * -0.0919
            } else if (mask == "Always" || mask == "دائما") {
                mask_coef = 5 * -0.0919
            }

            if (washing == "Never" || washing == "مطلقا") {
                washing_coef = 2 * 0.465
            } else if (washing == "Rarely" || washing == "نادرا") {
                washing_coef = 1 * 0.465
            } else if (washing == "Sometimes" || washing == "أحيانا") {
                washing_coef = 0 * 0.465
            } else if (washing == "Often" || washing == "غالبا") {
                washing_coef = -1 * 0.465
            } else if (washing == "Always" || washing == "دائما") {
                washing_coef = -2 * 0.465
            }

            if (alcohol == "Never drank alcohol" || alcohol == "غير متعاطي للكحول مطلقاً") {
                alcohol_coef = -1 * -0.0527
            } else if (alcohol == "Zero days within the last 2 weeks" || alcohol == "ولا يوم خلال الأسبوعين الماضيين") {
                alcohol_coef = 0 * -0.0527
            } else if (alcohol == "One day within the last 2 weeks" || alcohol == "يوم واحد خلال الأسبوعين الماضيين") {
                alcohol_coef = 1 * -0.0527
            } else if (alcohol == "Two days within the last 2 weeks" || alcohol == "يومين خلال الأسبوعين الماضيين") {
                alcohol_coef = 2 * -0.0527
            } else if (alcohol == "Three days within the last 2 weeks" || alcohol == "ثلاثة أيام خلال الأسبوعين الماضيين") {
                alcohol_coef = 3 * -0.0527
            } else if (alcohol == "Four days within the last 2 weeks" || alcohol == "أربعة أيام خلال الأسبوعين الماضيين") {
                alcohol_coef = 4 * -0.0527
            } else if (alcohol == "Five days within the last 2 weeks" || alcohol == "خمسة أيام خلال الأسبوعين الماضيين") {
                alcohol_coef = 5 * -0.0527
            } else if (alcohol == "Six days within the last 2 weeks" || alcohol == "ستة أيام خلال الأسبوعين الماضيين") {
                alcohol_coef = 6 * -0.0527
            } else if (alcohol == "Seven days within the last 2 weeks" || alcohol == "سبعة أيام خلال الأسبوعين الماضيين") {
                alcohol_coef = 7 * -0.0527
            } else if (alcohol == "Eight days within the last 2 weeks" || alcohol == "ثمانية أيام خلال الأسبوعين الماضيين") {
                alcohol_coef = 8 * -0.0527
            } else if (alcohol == "Nine days within the last 2 weeks" || alcohol == "تسعة أيام خلال الأسبوعين الماضيين") {
                alcohol_coef = 9 * -0.0527
            } else if (alcohol == "Ten days within the last 2 weeks" || alcohol == "عشرة أيام خلال الأسبوعين الماضيين") {
                alcohol_coef = 10 * -0.0527
            } else if (alcohol == "Eleven days within the last 2 weeks" || alcohol == "أحد عشر يومًا خلال الأسبوعين الماضيين") {
                alcohol_coef = 11 * -0.0527
            } else if (alcohol == "Twelve days within the last 2 weeks" || alcohol == "اثني عشر يومًا خلال الأسبوعين الماضيين") {
                alcohol_coef = 12 * -0.0527
            } else if (alcohol == "Thirteen days within the last 2 weeks" || alcohol == "ثلاثة عشر يومًا خلال آخر أسبوعين") {
                alcohol_coef = 13 * -0.0527
            } else if (alcohol == "Everyday within the last 2 weeks" || alcohol == "كل يوم خلال الأسبوعين الماضيين") {
                alcohol_coef = 14 * -0.0527
            }


            age_multi = age_coef * c

//            println("Gender = $gender_coef")
//            println("Hand_sanitizer = $handSanitizer_coef")
//            println("Mask = $mask_coef")
//            println("Smoking = $smoking_coef")
//            println("Washing = $washing_coef")
//            println("Alcohol = $alcohol_coef")
//            println("Age = $age_multi")
//            println("Encounter = $encounter_multi")

        }


        val arr2 = page_2?.split(delim)?.toTypedArray()

        if (arr2.size > 1) {
            asthma = arr2.get(0)

            kidney = arr2.get(1)

            liver = arr2.get(2)

            immune = arr2.get(3)

            heart = arr2.get(4)

            lung = arr2.get(5)

            diabetes = arr2.get(6)

            hypertension = arr2.get(7)

            var hiv = arr2.get(8)

            if (asthma == "true") {
                asthma_coef = -0.205
            } else {
                asthma_coef = 0.0
            }

            if (kidney == "true") {
                kidney_coef = 1.217
            } else {
                kidney_coef = 0.0
            }

            if (liver == "true") {
                liver_coef = 0.767
            } else {
                liver_coef = 0.0
            }

            if (gender == "" && smoking == "" && washing == "" && hand_sanitizer == "" && mask == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_All_the_Fields)
                        view = customToast_negative
                    }.show()


                }

                buttonBack.setOnClickListener() {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_All_the_Fields)
                        view = customToast_negative
                    }.show()

                }
            } else if (smoking == "" && washing == "" && hand_sanitizer == "" && mask == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Rest_of_The_Fields)
                        view = customToast_negative
                    }.show()


                }
                buttonBack.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Rest_of_The_Fields)
                        view = customToast_negative
                    }.show()


                }
            } else if (washing == "" && hand_sanitizer == "" && mask == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Rest_of_The_Fields)
                        view = customToast_negative
                    }.show()


                }
                buttonBack.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Rest_of_The_Fields)
                        view = customToast_negative
                    }.show()

                }

            } else if (hand_sanitizer == "" && mask == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Rest_of_The_Fields)
                        view = customToast_negative
                    }.show()


                }
                buttonBack.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Rest_of_The_Fields)
                        view = customToast_negative
                    }.show()

                }

            } else if (gender == "") {
                buttonNext.setOnClickListener {


                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Gender_Field)
                        view = customToast_negative
                    }.show()


                }
                buttonBack.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Gender_Field)
                        view = customToast_negative
                    }.show()


                }

            } else if (smoking == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Smoking_Field)
                        view = customToast_negative
                    }.show()


                }

                buttonBack.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Smoking_Field)
                        view = customToast_negative
                    }.show()


                }

            } else if (washing == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Washing_Field)
                        view = customToast_negative
                    }.show()


                }

                buttonBack.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Washing_Field)
                        view = customToast_negative
                    }.show()

                }

            } else if (hand_sanitizer == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Hand_Sanitization_Field)
                        view = customToast_negative
                    }.show()

                }
                buttonBack.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Hand_Sanitization_Field)
                        view = customToast_negative
                    }.show()


                }

            } else if (mask == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Mask_Field)
                        view = customToast_negative
                    }.show()


                }

                buttonBack.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_The_Mask_Field)
                        view = customToast_negative
                    }.show()

                }

            } else {
                buttonNext.setOnClickListener() {
                    view_pager_test_now.setCurrentItem(view_pager_test_now.currentItem + 1, true)

                }

                buttonBack.setOnClickListener() {
                    view_pager_test_now.setCurrentItem(view_pager_test_now.currentItem - 1, true)

                }
            }

            if (heart == "true") {
                heart_coef = 0.274
            } else {
                heart_coef = 0.0
            }

            if (lung == "true") {
                lung_coef = 0.242
            } else {
                lung_coef = 0.0
            }

            if (diabetes == "true") {
                diabetes_coef = 0.302
            } else {
                diabetes_coef = 0.0
            }

            if (hiv == "true") {
                hiv_coef = 0.426
            } else {
                hiv_coef = 0.0
            }

//            println("Asthma = $asthma_coef")
//            println("Kidney = $kidney_coef")
//            println("liver = $liver_coef")
//            println("heart = $heart_coef")
//            println("lung = $lung_coef")
//            println("diabetes = $diabetes_coef")
//            println("hiv = $hiv_coef")
        }

        val arr3 = page_3?.split(delim)?.toTypedArray()

        if (arr3.size > 1) {
            fever = arr3.get(0)
            cough = arr3.get(1)
            tiredness = arr3.get(2)
            breathing_diff = arr3.get(3)
            chest_pain = arr3.get(4)
            loss_in_speach = arr3.get(5)

            if (fever == "true") {
                feverWeight = 1
            } else {
                feverWeight = 0
            }

            if (cough == "true") {
                coughWeight = 1
            } else {
                coughWeight = 0
            }

            if (tiredness == "true") {
                tirednessWeight = 1
            } else {
                tirednessWeight = 0
            }

            if (breathing_diff == "true") {
                breathingDiffWeight = 3
            } else {
                breathingDiffWeight = 0
            }

            if (chest_pain == "true") {
                chestPainWeight = 3
            } else {
                chestPainWeight = 0
            }

            if (loss_in_speach == "true") {
                lossInSpeachWeight = 3
            } else {
                lossInSpeachWeight = 0
            }

            weight =
                feverWeight + coughWeight + tirednessWeight + breathingDiffWeight + chestPainWeight + lossInSpeachWeight

            if (weight >= 6) {
                covid19Symptoms = 3.054
                covidBoolean = true
            } else {
                covid19Symptoms = 0.0
                covidBoolean = false
            }

            if (arr3.get(6).isEmpty()) {
                contacts_Covid = arr3.get(6)
            } else {
                b = arr3.get(6).toDouble()
                if (b <= 0) {
                    contactsCovid_coef = 0.0
                } else {
                    contactsCovid_coef = 1.389
                }
            }
            nursing_home = arr3.get(7)
            health_worker = arr3.get(8)


            if (nursing_home == "Yes" || nursing_home == "نعم") {
                nursing_coef = 2.705
            } else {
                nursing_coef = 0.0
            }

            if (health_worker == "Yes" || health_worker == "نعم") {
                healthWorker_coef = 0.977
            } else {
                healthWorker_coef = 0.0
            }

//            println("fever = $fever")
//            println("cough = $cough")
//            println("tiredness = $tiredness")
//            println("breathing_diff = $breathing_diff")
//            println("chest_pain = $chest_pain")
//            println("loss_in_speach = $loss_in_speach")
//            println("contacts_covid = $contactsCovid_coef")
//            println("nursing_home = $nursing_coef")
//            println("health_worker = $healthWorker_coef")
//


        }
        if (page == 3) {
            if (nursing_home == "" && health_worker == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_All_the_Fields)
                        view = customToast_negative
                    }.show()


                }
            } else if (nursing_home == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_the_Nursing_Home_Field)
                        view = customToast_negative
                    }.show()


                }
            } else if (health_worker == "") {
                buttonNext.setOnClickListener {

                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.Please_Fill_the_Health_Worker_Field)
                        view = customToast_negative
                    }.show()


                }
            } else {
                tester = exp((encounter_multi))
                tester2 = exp((washing_coef))
                tester3 = exp((handSanitizer_coef))
                tester4 = exp((mask_coef))
                tester5 = exp((smoking_coef))
                if (tester > 1) {
                    tester -= 1
                    tester *= 100
                }
                if (tester < 1) {
                    tester = 1 - tester
                    tester *= -100
                }

                if (tester2 > 1) {
                    tester2 -= 1
                    tester2 *= 100
                }
                if (tester2 < 1) {
                    tester2 = 1 - tester2
                    tester2 *= -100
                }

                if (tester3 > 1) {
                    tester3 -= 1
                    tester3 *= 100
                }
                if (tester3 < 1) {
                    tester3 = 1 - tester3
                    tester3 *= -100
                }

                if (tester4 > 1) {
                    tester4 -= 1
                    tester4 *= 100
                }
                if (tester4 < 1) {
                    tester4 = 1 - tester4
                    tester4 *= -100
                }

                if (tester5 > 1) {
                    tester5 -= 1
                    tester5 *= 100
                }
                if (tester5 < 1) {
                    tester5 = 1 - tester5
                    tester5 *= -100
                }

                val const = -7.754
                var alg1 =
                    gender_coef + age_multi + smoking_coef + alcohol_coef + washing_coef + handSanitizer_coef + mask_coef + encounter_multi + asthma_coef + kidney_coef + liver_coef + heart_coef + lung_coef + diabetes_coef + hiv_coef + contactsCovid_coef + nursing_coef + healthWorker_coef + covid19Symptoms + const
//                        println("total = $alg1")
                var alg2 = exp(alg1)
//                        println("exponential value = $alg2")


                total = alg2 / (1 - alg2)

                if (total > 1.0) {
                    total = .999
                } else if (total < 0.0000) {
                    total = 0.999
                } else if (total == 1.0) {
                    total = 0.999
                }

                buttonNext.setOnClickListener()
                {

                    dialog = this?.let { Dialog(it) }!!
                    dialog.setContentView(R.layout.full_screen_progress_bar)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.setCancelable(false)
                    dialog.show()

                    var user = mAuth.currentUser?.uid

                    var userData = userData(
                        region,
                        diagnosedWithCovid,
                        gender,
                        c.toInt(),
                        smoking,
                        washing,
                        hand_sanitizer,
                        mask,
                        encounter,
                        asthma,
                        kidney,
                        liver,
                        immune,
                        heart,
                        lung,
                        diabetes,
                        hypertension,
                        hiv,
                        fever,
                        cough,
                        tiredness,
                        breathing_diff,
                        chest_pain,
                        loss_in_speach,
                        contacts_Covid,
                        nursing_home,
                        health_worker
                    )

                    if (user != null) {
                        mDatabase.child("User Data").child(user).setValue(userData)
                        mDatabase.child("User Data").child(user).child("Covid19 Symptoms")
                            .setValue(covidBoolean.toString())
                    }

                    var collectionReference = mFirestore.collection("UserData")
                    collectionReference.add(userData)
                    fetchJsonDaily()
                    fetchJson()

                }
            }

        }


    }


    private fun pageChange() {
        view_pager_test_now.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                page = position

                when (page) {
                    0 -> {
                        buttonBack.text = getString(R.string.BACK)
                        buttonBack.visibility = View.GONE
                        buttonNext.visibility = View.VISIBLE
                        buttonNext.text = getString(R.string.NEXT)
                        val current = resources.configuration.locale.toString()


                    }

                    1 -> {

                        buttonNext.text = getString(R.string.NEXT)
                        buttonBack.text = getString(R.string.BACK)

                        backArrow.setOnClickListener {
                            dialog = Dialog(this@TestNowActivity)
                            dialog.setContentView(R.layout.alert_quit_testnow_dialogue)
                            dialog.setCancelable(false)
                            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog.show()

                            dialog.buttonYesQuitTest.setOnClickListener {
                                finish()
                            }

                            dialog.buttonNoQuitTest.setOnClickListener {
                                dialog.dismiss()
                            }

                            dialog.closeQuit_to_dashboard.setOnClickListener {
                                dialog.dismiss()
                            }

                        }


                        buttonNext.visibility = View.VISIBLE
                        buttonBack.visibility = View.VISIBLE


                        val current = resources.configuration.locale.toString()

                    }
                    2 -> {
                        buttonBack.text = getString(R.string.BACK)
                        buttonBack.visibility = View.VISIBLE
                        buttonNext.visibility = View.VISIBLE

                        val current = resources.configuration.locale.toString()

                        buttonNext.text = getString(R.string.NEXT)

                    }

                    3 -> {
                        buttonBack.text = getString(R.string.BACK)
                        buttonBack.visibility = View.GONE
//                        buttonBack.visibility = View.VISIBLE
                        buttonNext.visibility = View.VISIBLE
                        buttonNext.text = getString(R.string.calculate)
                        val customToast_negative: View =
                            layoutInflater.inflate(
                                R.layout.custom_toast_negative,
                                fl_wrapper_fill_all_fields_toast
                            )

                        val customToast_positive: View =
                            layoutInflater.inflate(
                                R.layout.custom_toast_positive,
                                fl_wrapper_fill_all_fields_toast
                            )

                        val current = resources.configuration.locale.toString()

                        buttonNext.setOnClickListener {

                            Toast(this@TestNowActivity).apply {
                                customToast_negative.toast_text.text =
                                    getString(R.string.Please_Fill_All_the_Fields)
                                view = customToast_negative
                            }.show()


                        }

                    }

                }

            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    //    Location Functions----------------------------------------------------------------------------------------------------------------------------------------------------
    private fun getLastLocation() {

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
                        aa = getCountryName(location.latitude, location.longitude)
                        iso = getCountryCode(location.latitude, location.longitude)
                    }
                }
            } else {

                val current = resources.configuration.locale.toString()

                Toast(this@TestNowActivity).apply {
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
            aa = getCountryName(lastLocation.latitude, lastLocation.longitude)
            iso = getCountryCode(lastLocation.latitude, lastLocation.longitude)

        }
    }

    private fun checkPermission(): Boolean {

        if (
            this?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED ||
            this?.let {
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
        this?.let {
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
            this?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }

    private fun getCountryName(lat: Double, long: Double): String {
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        aa = address.get(0).countryName

        return aa
    }

    private fun getCountryCode(lat: Double, long: Double): String {
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        iso = address.get(0).countryCode

        return iso
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

//  End of Location Functions------------

    fun fetchJsonDaily() {
        val url = "https://api.covid19api.com/country/$iso"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    println("wasss" + body)
                    val gson = GsonBuilder().create()

                    val CoronD = gson.fromJson(body, AvgDate::class.java)


                    runOnUiThread {

                        var d = "7"
                        var m = "10"
                        var y = "20"

                        var LatestDay = CoronD.count()

                        var counter = 0
                        //n.text = CoronD.get(176).date
                        var difference = 0
                        var totalDifference = 0
                        LatestDay -= 1

                        for (counter in 0..6) {
//                            println(CoronD.get(LatestDay).date)
//                            println(CoronD.get(LatestDay).confirmed)
                            var n1 = CoronD.get(LatestDay).confirmed
                            for (c2 in 0..0) {
                                LatestDay -= 1
//                                println(CoronD.get(LatestDay).confirmed)
                                var n2 = CoronD.get(LatestDay).confirmed
                                difference = n1 - n2
//                                println("Difference = $difference")
                                totalDifference += difference
//                                println("Total Difference = $totalDifference")

                            }
                        }
                        AverageCases = totalDifference / 7.0
                        println("huiiii" + AverageCases)

                        var SetTieme = "x"

                        //recycler_view.adapter = CountriesAdapter(CoronD)
                    }


                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Error")
            }

        })

    }

    fun fetchJson() {

        val url = "https://corona.lmao.ninja/v2/countries?yesterday&sort"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()
                val gson = GsonBuilder().create()

                val CoronD = gson.fromJson(body, CoronaData::class.java)


                runOnUiThread {
                    println("asdsad" + AverageCases)
                    var index = -1
                    for (i in CoronD.indices) {
                        var tester = CoronD.get(i).countryInfo.iso2

                        val countryDefault: Locale = Locale.Builder().setRegion(tester).build()

                        var currentLanguage: String = Locale.getDefault().displayLanguage

                        var langSelector: Locale = Locale.Builder().setLanguage("en").build()

                        val current = resources.configuration.locale.toString()

                        println("current is : $current")
                        if (current == "ar") {
                            langSelector = Locale.Builder().setLanguage("ar").build()
                        }

                        if (currentLanguage.toLowerCase(Locale.ROOT).contains("العربية")) {
                            langSelector = Locale.Builder().setLanguage("ar").build()

                        }
                        if (countryDefault.getDisplayCountry(langSelector) == aa || CoronD.get(i).country == aa) {
                            index = i
                            break
                        }
                    }

                    var corona = CoronD.get(index)

                    CoronaMain(
                        total,
                        corona.cases,
                        AverageCases,
                        corona.population
                    ).addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            val e = task.exception
                            if (e is FirebaseFunctionsException) {

                                // Function error code, will be INTERNAL if the failure
                                // was not handled properly in the function call.
                                val code = e.code

                                // Arbitrary error details passed back from the function,
                                // usually a Map<String, Any>.
                                val details = e.details
                            }

                            // [START_EXCLUDE]
                            Log.w("addNumbers:onFailure", e)
                            return@addOnCompleteListener
                            // [END_EXCLUDE]
                        }

                        // [START_EXCLUDE]
                        val result = task.result

                        var user = mAuth.currentUser
                        if (!user!!.isAnonymous) {
                            mDatabase.child("results").child(user.uid).setValue(result)
                        }

                        var intent = Intent(applicationContext, TestResultActivity::class.java)
                        intent.putExtra("result", total.toString())
                        intent.putExtra("flag", corona.countryInfo.flag.toString())
                        intent.putExtra("daysSurvived", result.toString())
                        intent.putExtra("tester", tester.toString())
                        intent.putExtra("tester2", tester2.toString())
                        intent.putExtra("tester3", tester3.toString())
                        intent.putExtra("tester4", tester4.toString())
                        intent.putExtra("tester5", tester5.toString())
                        startActivity(intent)
                        finish()

                    }

                }

                dialog.dismiss()

            }

            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }


        })


    }

    fun CoronaMain(
        ChanceOfInfection: Double, totalInfected: Int, dailyInfected: Double,
        totalPopulation: Int
    ): Task<Int> {
        functions = FirebaseFunctions.getInstance()
        val data = hashMapOf(
            "ChanceOfInfection" to ChanceOfInfection,
            "totalInfected" to totalInfected,
            "dailyInfected" to dailyInfected,
            "totalPopulation" to totalPopulation
        )
        return functions
            .getHttpsCallable("addNumbers")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then task.result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as Map<*, *>
                result["roundsSurvived"] as Int
            }

    }


    fun prev(view: View?) {
        view_pager_test_now.setCurrentItem(view_pager_test_now.currentItem - 1, true)
    }

    fun next(view: View?) {
        view_pager_test_now.setCurrentItem(view_pager_test_now.currentItem + 1, true)
    }

    private fun userData(
        country: String,
        diagnosed: String,
        gender: String,
        age: Int,
        smoking: String,
        washing: String,
        handSanitizer: String,
        mask: String,
        encounter: String,
        asthma: String,
        kidney: String,
        liver: String,
        immune: String,
        heart: String,
        lung: String,
        diabetes: String,
        hypertension: String,
        hiv: String,
        fever: String,
        cough: String,
        tiredness: String,
        breathingDiff: String,
        chestPain: String,
        lossInSpeach: String,
        contactsCovid: String,
        nursingHome: String,
        healthWorker: String
    ): UserData {

        return UserData(
            country,
            diagnosed,
            gender,
            age,
            smoking,
            washing,
            handSanitizer,
            mask,
            encounter,
            asthma,
            kidney,
            liver,
            immune,
            heart,
            lung,
            diabetes,
            hypertension,
            hiv,
            fever,
            cough,
            tiredness,
            breathingDiff,
            chestPain,
            lossInSpeach,
            contactsCovid,
            nursingHome,
            healthWorker
        )
    }

    private fun setupViewPager() {

        val adapter = MyViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(fragment0)
        adapter.addFragment(fragment1)
        adapter.addFragment(fragment2)
        adapter.addFragment(fragment3)

        view_pager_test_now.adapter = adapter
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


    override fun onBackPressed() {
        finish()
        super.onBackPressed()


    }

}

