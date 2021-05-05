package com.v1.Tammeni.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.v1.Tammeni.Activities.TestNowActivity
import com.v1.Tammeni.R
import kotlinx.android.synthetic.main.fragment_testnow_2.*
import kotlinx.android.synthetic.main.fragment_testnow_2.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Testnow_fragment_1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Testnow_fragment_2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var asthma_True = false

    var kidney_True = false

    var liver_True = false

    var immune_True = false

    var heart_True = false

    var lung_True = false

    var diabetes_True = false

    var hypertension_True = false

    var hiv_True = false


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
        val view = inflater.inflate(R.layout.fragment_testnow_2, container, false)
        // Inflate the layout for this fragment

//        !view.checkbox_asthma.isChecked
//        !view.checkbox_kidney_disease.isChecked
//        !view.checkbox_liver_disease.isChecked
//        !view.checkbox_immunity_disease.isChecked
//        !view.checkbox_heart_disease.isChecked
//        !view.checkbox_Lung_disease.isChecked
//        !view.checkbox_diabetes.isChecked
//        !view.checkbox_hypertension.isChecked
//        !view.checkbox_HIV.isChecked

//        if (!view.checkbox_asthma.isChecked && !view.checkbox_kidney_disease.isChecked &&
//            !view.checkbox_liver_disease.isChecked && !view.checkbox_immunity_disease.isChecked &&
//            !view.checkbox_heart_disease.isChecked && !view.checkbox_Lung_disease.isChecked &&
//            !view.checkbox_diabetes.isChecked && !view.checkbox_hypertension.isChecked &&
//            !view.checkbox_HIV.isChecked
//        ) {
//            println("all not checked")
//        }
        val mn: TestNowActivity? = activity as TestNowActivity?
        var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
        if (mn != null) {
            mn.co(null,null,p2,null)
        }

        view.checkbox_asthma.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_asthma.isChecked) {
                asthma_True = true
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }

            }

            if (!checkbox_asthma.isChecked) {
                asthma_True = false
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }
        }

        view.checkbox_kidney_disease.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_kidney_disease.isChecked) {
                kidney_True = true
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }

            if (!checkbox_kidney_disease.isChecked) {
                kidney_True = false
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }
        }

        view.checkbox_liver_disease.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_liver_disease.isChecked) {
                liver_True = true
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }

            if (!checkbox_liver_disease.isChecked) {
                liver_True = false
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }
        }

        view.checkbox_immunity_disease.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_immunity_disease.isChecked) {
                immune_True = true
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }

            if (!checkbox_immunity_disease.isChecked) {
                immune_True = false
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }
        }

        view.checkbox_heart_disease.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_heart_disease.isChecked) {
                heart_True = true
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }

            if (!checkbox_heart_disease.isChecked) {
                heart_True = false

                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }
        }

        view.checkbox_Lung_disease.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_Lung_disease.isChecked) {
                lung_True = true
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }

            if (!checkbox_Lung_disease.isChecked) {
                lung_True = false
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }
        }

        view.checkbox_diabetes.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_diabetes.isChecked) {
                diabetes_True = true
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }

            if (!checkbox_diabetes.isChecked) {
                diabetes_True = false
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }
        }

        view.checkbox_hypertension.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_hypertension.isChecked) {
                hypertension_True = true
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }

            if (!checkbox_hypertension.isChecked) {
                hypertension_True = false
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }
        }

        view.checkbox_HIV.setOnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->

            if (checkbox_HIV.isChecked) {
                hiv_True = true
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }

            if (!checkbox_HIV.isChecked) {
                hiv_True = false
                var p2 = asthma_True.toString() + "/" + kidney_True + "/" + liver_True + "/" + immune_True + "/" + heart_True + "/"+ lung_True + "/" + diabetes_True + "/" + hypertension_True + "/" + hiv_True
                if (mn != null) {
                    mn.co(null,null,p2,null)
                }
            }
        }



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
}