package com.v1.Tammeni.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.v1.Tammeni.Activities.TestNowActivity
import com.v1.Tammeni.R
import kotlinx.android.synthetic.main.fragment_testnow_3.*
import kotlinx.android.synthetic.main.fragment_testnow_3.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [Testnow_fragment_1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Testnow_fragment_3 : Fragment() {
    var radiovalue_health_worker = ""
    var radiovalue_nursing_home = ""
    var item = ""
    var fever = false
    var cough = false
    var tiredness = false
    var breathing_diff = false
    var chest_pain = false
    var loss_in_speach = false

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        var view = inflater.inflate(R.layout.fragment_testnow_3, container, false)
        val list_COVID_contacts: MutableList<String> = ArrayList()

        val mn: TestNowActivity? = activity as TestNowActivity?
        var p2 =
            fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
        if (mn != null) {
            mn.co(null, null, null, p2)
        }

        view.checkbox_fever.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_fever.isChecked) {
                fever = true
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }

            if (!checkbox_fever.isChecked) {
                fever = false
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }
        }
        view.checkbox_dry_cough.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_dry_cough.isChecked) {
                cough = true
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }

            if (!checkbox_dry_cough.isChecked) {
                cough = false
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }
        }

        view.checkbox_tiredness.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_tiredness.isChecked) {
                tiredness = true
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }

            if (!checkbox_tiredness.isChecked) {
                tiredness = false
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }
        }

        view.checkbox_difficulty_of_breathing.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_difficulty_of_breathing.isChecked) {
                breathing_diff = true
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }

            if (!checkbox_difficulty_of_breathing.isChecked) {
                breathing_diff = false
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }
        }

        view.checkbox_chest_pain.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_chest_pain.isChecked) {
                chest_pain = true
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }

            if (!checkbox_chest_pain.isChecked) {
                chest_pain = false
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }
        }

        view.checkbox_loss_speech_movement.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_loss_speech_movement.isChecked) {
                loss_in_speach = true
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }

            if (!checkbox_loss_speech_movement.isChecked) {
                loss_in_speach = false
                var p2 =
                    fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                if (mn != null) {
                    mn.co(null, null, null, p2)
                }
            }
        }



        for (i in 0..20)
            list_COVID_contacts.add("$i")



        val adapter_COVID_contacts = activity?.let { it1 ->
            ArrayAdapter(
                it1,
                R.layout.custom_spinner,
                list_COVID_contacts
            )

        }

        adapter_COVID_contacts?.setDropDownViewResource(R.layout.custom_spinner_dropdown)

        view.spinner_contactswithCOVID.adapter = adapter_COVID_contacts


        view.spinner_contactswithCOVID.onItemSelectedListener =
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
                    item = list_COVID_contacts[position]
                    var p2 =
                        fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
                    if (mn != null) {
                        mn.co(null, null, null, p2)
                    }

                }
            }

        limitDropDownHeight(view.spinner_contactswithCOVID)



        view.radioGroup_nursing_home.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton? = activity?.findViewById(checkedId)

            radiovalue_nursing_home = radio?.text.toString()
            var p2 =
                fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
            if (mn != null) {
                mn.co(null, null, null, p2)
            }

        }


        view.radioGroup_health_worker.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton? = activity?.findViewById(checkedId)

            radiovalue_health_worker = radio?.text.toString()
            var p2 =
                fever.toString() + "/" + cough + "/" + tiredness + "/" + breathing_diff + "/" + chest_pain + "/" + loss_in_speach + "/" + item + "/" + radiovalue_nursing_home + "/" + radiovalue_health_worker
            if (mn != null) {
                mn.co(null, null, null, p2)
            }

        }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Testnow_fragment_1.
         */
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

    private fun limitDropDownHeight(spinner: Spinner) {
        val popup = Spinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true

//        val popupWindow = popup.get(spinner) as ListPopupWindow
//        popupWindow.height = (50 * resources.displayMetrics.density).toInt()
    }
}