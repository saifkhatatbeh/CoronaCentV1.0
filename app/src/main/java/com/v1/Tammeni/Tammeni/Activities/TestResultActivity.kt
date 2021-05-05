package com.v1.Tammeni.Activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.v1.Tammeni.R
import com.v1.Tammeni.data.Leaderboard_data_items
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_test_result.*
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*
import java.math.RoundingMode

class TestResultActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase: DatabaseReference
    var userName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)

        val current = resources.configuration.locale.toString()

        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

        val customToast_positive: View =
            layoutInflater.inflate(R.layout.custom_toast_positive, fl_wrapper_fill_all_fields_toast)

        val green = getColor(R.color.colorPrimary)
        val greenLight = getColor(R.color.colorLight)
        val grey = getColor(R.color.colorPrimaryText)

        val total = intent.getStringExtra("result")
        val percentage = total?.toDouble()?.times(100.0)
        val totalSurvived = intent.getStringExtra("daysSurvived")

        val decimal = percentage?.toBigDecimal()
            ?.setScale(3, RoundingMode.UP)
            ?.toFloat()


        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference


        var user = mAuth.currentUser

        if (user!!.isAnonymous) {
            buttonSaveToLeaderboard.visibility = View.GONE
        }

        button_go_to_dashboard.setOnClickListener {
            finish()
        }

        progress_circular_Infection_Probability.apply {

            var infection_probability = decimal
            progressMax = 100f
            if (infection_probability != null) {
                setProgressWithAnimation(infection_probability, 2000)
            }
            progressBarWidth = 15f
            backgroundProgressBarWidth = 8f
            progressBarColor = green
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Infection_probability_circle.text =
                (infection_probability.toString() + " %")

        }

//        progress_circular_Fatality_Probability.apply {
//
//            var fatality_probability = 20f
//            progressMax = 100f
//            setProgressWithAnimation(fatality_probability, 2000)
//            progressBarWidth = 15f
//            backgroundProgressBarWidth = 8f
//            progressBarColor = green
//            roundBorder = true
//            backgroundProgressBarColor = greenLight
//            Fatality_probability_circle.text =
//                ("$fatality_probability %")
//        }
//
//        progress_circular_Infection_Probability_2weeks.apply {
//
//            var infection_probability_2weeks = 35f
//            progressMax = 100f
//            setProgressWithAnimation(infection_probability_2weeks, 2000)
//            progressBarWidth = 15f
//            backgroundProgressBarWidth = 8f
//            progressBarColor = green
//            roundBorder = true
//            backgroundProgressBarColor = greenLight
//            Infection_probability2weeks_circle.text =
//                ("$infection_probability_2weeks %")
//        }

        progress_circular_Days_Survived.apply {

            var days_survived = totalSurvived!!.toFloat()
            progressMax = 365f
            setProgressWithAnimation(days_survived, 3000)
            progressBarWidth = 15f
            backgroundProgressBarWidth = 8f
            progressBarColor = green
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Days_survived_circle.setText(days_survived.toInt().toString())
        }


//        Suggestions (Good)----------------------------------------------------------------------------------------------------//

        progress_circular_Social_Distancing_suggestion_good.apply {
            val total = intent.getStringExtra("tester")

            if (total != null) {
                if(total.toDouble() > 0) {
                    linear_social_distancing_good_habit.visibility = View.GONE

                }
            }

            val social_distance = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(social_distance, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            roundBorder = true
            backgroundProgressBarColor = greenLight

            Social_Distancing_suggestion_circle_good.text =
                (social_distance.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..50000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (social_distance.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }

        }

        progress_circular_Washing_Hands_suggestion_good.apply {
            val total = intent.getStringExtra("tester2")


            if (total != null) {
                if(total.toDouble() > 0) {
                    linear_washing_hands_good_habit.visibility = View.GONE
                }
            }

            val washing_hands = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(washing_hands, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Washing_Hands_suggestion_circle_good.text =
                (washing_hands.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..1000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (washing_hands.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }
        }

        progress_circular_Alcohol_Sanitizer_suggestion_good.apply {
            val total = intent.getStringExtra("tester3")

            if (total != null) {
                if(total.toDouble() > 0) {
                    linear_alcohol_sanitizer_good_habit.visibility = View.GONE

                }
            }

            val alcohol_sanitization_factor = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(alcohol_sanitization_factor, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Alcohol_Sanitizer_suggestion_circle_good.text =
                (alcohol_sanitization_factor.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..1000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (alcohol_sanitization_factor.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }
        }

        progress_circular_Wearing_Mask_suggestion_good.apply {
            val total = intent.getStringExtra("tester4")

            if (total != null) {
                if(total.toDouble() > 0) {
                    linear_wearing_mask_good_habit.visibility = View.GONE

                }
            }

            val wearing_mask = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(wearing_mask, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Wearing_Mask_suggestion_circle_good.text =
                (wearing_mask.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..1000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (wearing_mask.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }
        }

        progress_circular_Smoking_suggestion_good.apply {
            val total = intent.getStringExtra("tester5")


            if (total != null) {
                if(total.toDouble() > 0) {
                    linear_smoking_good_habit.visibility = View.GONE

                }
            }

            val smoking_factor = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(smoking_factor, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Smoking_suggestion_circle_good.text =
                (smoking_factor.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..1000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (smoking_factor.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }
        }

        //Suggestions (Bad)----------------------------------------------------------------------------------------------------//

        progress_circular_Social_Distancing_suggestion_bad.apply {
            val total = intent.getStringExtra("tester")


            if (total != null) {
                if(total.toDouble() < 0) {
                    linear_social_distancing_bad_habit.visibility = View.GONE

                }
            }

            val social_distance = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(social_distance, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            roundBorder = true
            backgroundProgressBarColor = greenLight

            Social_Distancing_suggestion_circle_bad.text =
                (social_distance.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..50000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (social_distance.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }

        }

        progress_circular_Washing_Hands_suggestion_bad.apply {
            val total = intent.getStringExtra("tester2")


            if (total != null) {
                if(total.toDouble() < 0) {
                    linear_washing_hands_bad_habit.visibility = View.GONE

                }
            }
            val washing_hands = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(washing_hands, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            progressBarColor = grey
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Washing_Hands_suggestion_circle_bad.text =
                (washing_hands.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..1000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (washing_hands.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }
        }

        progress_circular_Alcohol_Sanitizer_suggestion_bad.apply {
            val total = intent.getStringExtra("tester3")


            if (total != null) {
                if(total.toDouble() < 0) {
                    linear_alcohol_sanitizer_bad_habit.visibility = View.GONE

                }
            }


            val alcohol_sanitization_factor = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(alcohol_sanitization_factor, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Alcohol_Sanitizer_suggestion_circle_bad.text =
                (alcohol_sanitization_factor.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..1000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (alcohol_sanitization_factor.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }
        }

        progress_circular_Wearing_Mask_suggestion_bad.apply {
            val total = intent.getStringExtra("tester4")


            if (total != null) {
                if(total.toDouble() < 0) {
                    linear_wearing_mask_bad_habit.visibility = View.GONE

                }
            }

            val wearing_mask = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(wearing_mask, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            progressBarColor = grey
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Wearing_Mask_suggestion_circle_bad.text =
                (wearing_mask.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..1000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (wearing_mask.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }
        }

        progress_circular_Smoking_suggestion_bad.apply {
            val total = intent.getStringExtra("tester5")


            if (total != null) {
                if(total.toDouble() < 0) {
                    linear_smoking_bad_habit.visibility = View.GONE

                }
            }


            val smoking_factor = total!!.toFloat()
            progressMax = 100f
            setProgressWithAnimation(smoking_factor, 5000)
            progressBarWidth = 13f
            backgroundProgressBarWidth = 6f
            progressBarColor = grey
            roundBorder = true
            backgroundProgressBarColor = greenLight
            Smoking_suggestion_circle_bad.text =
                (smoking_factor.toInt().toString() + " %")

            val range25 = 0..25
            val range50 = 25..50
            val range75 = 50..75
            val range100 = 75..1000

            val rangedown25 = 0 downTo -25
            val rangedown50 = -25 downTo -50
            val rangedown75 = -50 downTo -75
            val rangedown100 = -75 downTo -1000

            when (smoking_factor.toInt()) {
                //0 to 100
                in range25 -> {
                    progressBarColor = Color.parseColor("#FFFF00")
                }
                in range50 -> {
                    progressBarColor = Color.parseColor("#FFCC00")
                }
                in range75 -> {
                    progressBarColor = Color.parseColor("#ff9900")
                }
                in range100 -> {
                    progressBarColor = Color.parseColor("#ff6600")
                }

                //0 to -100
                in rangedown25 -> {
                    progressBarColor = Color.parseColor("#00D4B0")
                }
                in rangedown50 -> {
                    progressBarColor = Color.parseColor("#00B7D8")
                }
                in rangedown75 -> {
                    progressBarColor = Color.parseColor("#188AF0")
                }
                in rangedown100 -> {
                    progressBarColor = Color.parseColor("#146CF6")
                }
            }
        }

//        End of suggestions

        buttonSaveToLeaderboard.setOnClickListener() {

            Toast(this).apply {
                customToast_positive.toast_text.text =
                    getString(R.string.Successfully_saved_to_leaderboard)
                view = customToast_positive
            }.show()

            val result = intent.getStringExtra("daysSurvived")
            val flag = intent.getStringExtra("flag")

            val path = user?.uid
            user.let {
                userName = user?.displayName.toString()
            }

            //Rank Calculations Goes Here

            val leaderboard = leaderBoard(1, userName, result!!.toInt(), flag!!)

            mDatabase.child("Score").child(path).setValue(leaderboard)
        }

        buttonShare.setOnClickListener {
            val x = decimal

            val intent = Intent()
            intent.action = Intent.ACTION_SEND

            val arabic1 = " فرصتي للإصابة بفيروس كورونا هي: $x% "
            val arabic2 = " سأعيش $totalSurvived يومًا / أيام قبل الإصابة!"
            val arabic3 =
                "تحقق من احتمالية إصابتك بالعدوى والأيام التي ستعيش فيها عن طريق تنزيل Tammeni من متجر Google PlayStore:"

            if (current.contains("ar") ) {

                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "$arabic1 \n $arabic2 \n $arabic3"
                )


            } else {
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "My chance of getting infected with COVID-19 is: $x % \nI will survive $totalSurvived day/s before getting infected! \nCheck your probability of getting infected and days you will survive by downloading Tammeni from Google PlayStore: "
                )

            }

            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Please select sharing app: "))
        }

    }

    private fun leaderBoard(
        rank: Int,
        userName: String,
        daysSurvived: Int,
        countryIMG: String
    ): Leaderboard_data_items {
        return Leaderboard_data_items(rank, userName, daysSurvived, countryIMG)
    }

}