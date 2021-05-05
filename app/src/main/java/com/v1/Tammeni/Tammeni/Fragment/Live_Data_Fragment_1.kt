package com.v1.Tammeni.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.v1.Tammeni.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class BlankFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_slider_livedata_1, container, false)
        var textView: TextView? = null
        var textView2: TextView? = null
        var textView3: TextView? = null

        textView = view.findViewById(R.id.txtActiveCases)
        textView2 = view.findViewById(R.id.txtDailyCases)
        textView3 = view.findViewById(R.id.txtNewDeaths)


        textView.text = param1.toString()
        textView2.text = param2.toString()
        textView3.text = param3.toString()

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }
}