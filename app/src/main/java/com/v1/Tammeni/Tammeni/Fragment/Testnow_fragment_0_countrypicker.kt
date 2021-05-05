package com.v1.Tammeni.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.v1.Tammeni.Activities.TestNowActivity
import com.v1.Tammeni.R
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.fragment_testnow_0_countrypicker.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Testnow_fragment_1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Testnow_fragment_0_countrypicker : Fragment() {
    // TODO: Rename and change types of parameters
    var diagnosed_with_COVID = ""
    private var param1: String? = null
    private var param2: String? = null
    lateinit var countryCodePicker: CountryCodePicker
    var radiovalue_diagnosed_with_covid = ""

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
        val view: View =
            inflater.inflate(R.layout.fragment_testnow_0_countrypicker, container, false)

        val mn: TestNowActivity? = activity as TestNowActivity?
        countryCodePicker = view?.findViewById(R.id.country_picker)!!

        countryCodePicker.setCcpClickable(false)
        countryCodePicker.setAutoDetectedCountry(true)

        view.radioGroup_diagnosed_with_COVID.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton? = (activity as TestNowActivity?)?.findViewById(checkedId)

            diagnosed_with_COVID = radio?.text.toString()
            var p0 = countryCodePicker.selectedCountryName.toString() + "/" + diagnosed_with_COVID
            mn?.co(p0, null, null, null)

        }

        var p0 = countryCodePicker.selectedCountryName.toString() + "/" + diagnosed_with_COVID
        mn?.co(p0, null, null, null)


//        countryCodePicker.setOnCountryChangeListener {
//            if (mn != null) {
//                mn.co(countryCodePicker.selectedCountryName.toString(),null,null,null)
//            }
//        }


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


//        countryCodePicker = activity?.findViewById(R.id.country_picker)!!
//
//        spinnerResult.text = countryCodePicker.selectedCountryName
//
//        countryCodePicker.setOnCountryChangeListener {
//            spinnerResult.text = countryCodePicker.selectedCountryName
//        }
//
//

    }
}