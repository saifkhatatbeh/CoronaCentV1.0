package com.v1.Tammeni.Fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.v1.Tammeni.Activities.TestNowActivity
import com.v1.Tammeni.R
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.fragment_testnow_1.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Testnow_fragment_1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Testnow_fragment_1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var countryCodePicker: CountryCodePicker
    var a = ""
    var item1 = ""
    var item2 = ""
    var item3 = ""
    var radiovalue_gender = ""
    var radiovalue_smoking = ""
    var radiovalue_washing = ""
    var radiovalue_hand_sanitizer = ""
    var radiovalue_handsanitizer = ""
    var radiovalue_mask = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val list_alcohol: MutableList<String> = ArrayList()
        val list_age: MutableList<String> = ArrayList()
        val list_encounter: MutableList<String> = ArrayList()

        val view = inflater.inflate(R.layout.fragment_testnow_1, container, false)

        view.info_smoking.setOnClickListener() {
            val dialog: Dialog = activity?.let { Dialog(it) }!!

            dialog.setContentView(R.layout.info_smoking_dialog)
            val close = dialog.findViewById(R.id.close_info_smoking) as ImageView
            val buttonClose = dialog.findViewById(R.id.button_close_info_smoking) as Button

            dialog.show()

            close.setOnClickListener() {
                dialog.dismiss()
            }

            buttonClose.setOnClickListener() {
                dialog.dismiss()
            }

        }


        view.radioGroup_gender.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton? = activity?.findViewById(checkedId)

            radiovalue_gender = radio?.text.toString()
            val mn: TestNowActivity? = activity as TestNowActivity?
            var p1 =
                radiovalue_gender + "/" + radiovalue_handsanitizer + "/" + radiovalue_mask + "/" + radiovalue_smoking + "/" + radiovalue_washing + "/" + item1 + "/" + item2 + "/" + item3
            if (mn != null) {
                mn.co(null, p1, null, null)
            }

        }



        view.radioGroup_smoking.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton? = activity?.findViewById(checkedId)

            radiovalue_smoking = radio?.text.toString()

            val mn: TestNowActivity? = activity as TestNowActivity?
            var p1 =
                radiovalue_gender + "/" + radiovalue_handsanitizer + "/" + radiovalue_mask + "/" + radiovalue_smoking + "/" + radiovalue_washing + "/" + item1 + "/" + item2 + "/" + item3
            if (mn != null) {
                mn.co(null, p1, null, null)
            }

        }


        view.radioGroup_wash_hands.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton? = activity?.findViewById(checkedId)

            radiovalue_washing = radio?.text.toString()
            val mn: TestNowActivity? = activity as TestNowActivity?
            var p1 =
                radiovalue_gender + "/" + radiovalue_handsanitizer + "/" + radiovalue_mask + "/" + radiovalue_smoking + "/" + radiovalue_washing + "/" + item1 + "/" + item2 + "/" + item3
            if (mn != null) {
                mn.co(null, p1, null, null)
            }

        }


        view.radioGroup_hand_sanitizer.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton? = activity?.findViewById(checkedId)

            radiovalue_hand_sanitizer = radio?.text.toString()
            val mn: TestNowActivity? = activity as TestNowActivity?
            var p1 =
                radiovalue_gender + "/" + radiovalue_handsanitizer + "/" + radiovalue_mask + "/" + radiovalue_smoking + "/" + radiovalue_washing + "/" + item1 + "/" + item2 + "/" + item3
            if (mn != null) {
                mn.co(null, p1, null, null)
            }

        }


        view.radioGroup_hand_sanitizer.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton? = activity?.findViewById(checkedId)

            radiovalue_handsanitizer = radio?.text.toString()
            val mn: TestNowActivity? = activity as TestNowActivity?
            var p1 =
                radiovalue_gender + "/" + radiovalue_handsanitizer + "/" + radiovalue_mask + "/" + radiovalue_smoking + "/" + radiovalue_washing + "/" + item1 + "/" + item2 + "/" + item3
            if (mn != null) {
                mn.co(null, p1, null, null)
            }

        }


        view.radioGroup_mask.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton? = activity?.findViewById(checkedId)

            radiovalue_mask = radio?.text.toString()
            val mn: TestNowActivity? = activity as TestNowActivity?
            var p1 =
                radiovalue_gender + "/" + radiovalue_handsanitizer + "/" + radiovalue_mask + "/" + radiovalue_smoking + "/" + radiovalue_washing + "/" + item1 + "/" + item2 + "/" + item3
            if (mn != null) {
                mn.co(null, p1, null, null)
            }

        }

        for (i in 0..200)
            list_encounter.add("$i")

        for (i in 1..120)
            list_age.add("$i")

        list_alcohol.add(getString(R.string.Never_drank_alcohol))
        list_alcohol.add(getString(R.string.Zero_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.One_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Two_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Three_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Four_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Five_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Six_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Seven_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Eight_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Nine_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Ten_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Eleven_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Twelve_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Thirteen_days_within_the_last_2_weeks))
        list_alcohol.add(getString(R.string.Everyday_days_within_the_last_2_weeks))

        val adapter_alcohol = activity?.let { it1 ->
            ArrayAdapter(
                it1,
                R.layout.custom_spinner,
                list_alcohol
            )

        }

        val adapter_age = activity?.let { it1 ->
            ArrayAdapter(
                it1,
                R.layout.custom_spinner,
                list_age
            )

        }

        val adapter_encounter = activity?.let { it1 ->
            ArrayAdapter(
                it1,
                R.layout.custom_spinner,
                list_encounter
            )

        }

        adapter_alcohol?.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        adapter_age?.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        adapter_encounter?.setDropDownViewResource(R.layout.custom_spinner_dropdown)

        view.spinner_alcohol.adapter = adapter_alcohol
        view.spinner_age.adapter = adapter_age
        view.spinner_people_encounter.adapter = adapter_encounter

        view.spinner_alcohol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                item1 = list_alcohol[position]

                val mn: TestNowActivity? = activity as TestNowActivity?
                var p1 =
                    radiovalue_gender + "/" + radiovalue_handsanitizer + "/" + radiovalue_mask + "/" + radiovalue_smoking + "/" + radiovalue_washing + "/" + item1 + "/" + item2 + "/" + item3
                if (mn != null) {
                    mn.co(null, p1, null, null)
                }

            }
        }

        view.spinner_age.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                item2 = list_age[position]

                val mn: TestNowActivity? = activity as TestNowActivity?
                var p1 =
                    radiovalue_gender + "/" + radiovalue_handsanitizer + "/" + radiovalue_mask + "/" + radiovalue_smoking + "/" + radiovalue_washing + "/" + item1 + "/" + item2 + "/" + item3
                if (mn != null) {
                    mn.co(null, p1, null, null)
                }

            }
        }

        view.spinner_people_encounter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    item3 = list_encounter[position]
                    val mn: TestNowActivity? = activity as TestNowActivity?
                    var p1 =
                        radiovalue_gender + "/" + radiovalue_handsanitizer + "/" + radiovalue_mask + "/" + radiovalue_smoking + "/" + radiovalue_washing + "/" + item1 + "/" + item2 + "/" + item3
                    if (mn != null) {
                        mn.co(null, p1, null, null)
                    }

                }
            }



        limitDropDownHeight(view.spinner_alcohol)
        limitDropDownHeight(view.spinner_age)
        limitDropDownHeight(view.spinner_people_encounter)


        // Inflate the layout for this fragment
        return view
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Testnow_fragment_1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


    private fun limitDropDownHeight(spinner: Spinner) {
        val popup = Spinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true

//        val popupWindow = popup.get(spinner) as ListPopupWindow
//        popupWindow.height = (50 * resources.displayMetrics.density).toInt()
    }
}