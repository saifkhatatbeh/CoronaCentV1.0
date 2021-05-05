package com.v1.Tammeni.Activities


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.v1.Tammeni.Adapters.CountriesAdapter
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.R
import com.v1.Tammeni.data.CoronaData
import com.v1.Tammeni.data.CoronaDataItem
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_countries.*
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class CountriesActivity : AppCompatActivity() {
    private lateinit var displayList: MutableList<CoronaDataItem>
    private lateinit var editText: EditText
    lateinit var dialog: Dialog

    lateinit var dialog_network: Dialog


    private lateinit var adapter: CountriesAdapter
    private lateinit var mRecyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)

        dialog = this?.let { Dialog(it) }!!
        dialog.setContentView(R.layout.full_screen_progress_bar)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
        val current = resources.configuration.locale
        println("The default is $current")

        dialog_network = Dialog(this)
        dialog_network.setContentView(R.layout.negative_custom_dialogue)

        val nConnection = NetworkConnection(this)
        nConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
            if (isConnected) {

                fetchJson()

                var on = this.let { ContextCompat.getColor(it, R.color.colorPrimary) }
                var off = this.let { ContextCompat.getColor(it, R.color.colorSecondaryText) }

                btnNow.setTextColor(on)
                btnYest.setTextColor(off)
                btnNow.setOnClickListener {
                    dialog = this?.let { Dialog(it) }!!
                    dialog.setContentView(R.layout.full_screen_progress_bar)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.setCancelable(false)
                    dialog.show()

                    search.setText("")
                    btnNow.setTextColor(on)
                    btnYest.setTextColor(off)
                    fetchJson()

                }

                btnYest.setOnClickListener {
                    dialog = this?.let { Dialog(it) }!!
                    dialog.setContentView(R.layout.full_screen_progress_bar)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.setCancelable(false)
                    dialog.show()

                    search.setText("")
                    btnYest.setTextColor(on)
                    btnNow.setTextColor(off)
                    fetchJson2()
                }


            } else {
                val customToast_negative: View =
                    layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

                Toast(this).apply {
                    customToast_negative.toast_text.text =
                        getString(R.string.No_Internet_Connection)
                    view = customToast_negative
                }.show()

                finish()
            }
        })
//        mRecyclerView = findViewById(R.id.recycler_view)
//        editText = findViewById(R.id.search)
//        adapter = CountriesAdapter(mutableListOf())
//        mRecyclerView.adapter = adapter
//        displayList = mutableListOf()

        backArrow_countries.setOnClickListener {
            finish()
        }


    }


    fun fetchJson() {

        mRecyclerView = findViewById(R.id.recycler_view)
        editText = findViewById(R.id.search)
        adapter = CountriesAdapter(mutableListOf())
        mRecyclerView.adapter = adapter
        displayList = mutableListOf()
        val url = "https://corona.lmao.ninja/v2/countries?yesterday&sort"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()

                val CoronD = gson.fromJson(body, CoronaData::class.java)


                runOnUiThread {
                    adapter = CountriesAdapter(CoronD)
                    var layoutManager = LinearLayoutManager(this@CountriesActivity)
                    mRecyclerView.layoutManager = layoutManager
                    mRecyclerView.adapter = adapter

                    dialog.dismiss()

                    editText.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(p0: Editable?) {

                            filterList(p0.toString(), CoronD)
                        }

                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }

                    })

                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }


        })


    }


    fun fetchJson2() {

        mRecyclerView = findViewById(R.id.recycler_view)
        editText = findViewById(R.id.search)
        adapter = CountriesAdapter(mutableListOf())
        mRecyclerView.adapter = adapter
        displayList = mutableListOf()
        val url = "https://corona.lmao.ninja/v2/countries?yesterday=true&sort"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val CoronD = gson.fromJson(body, CoronaData::class.java)


                runOnUiThread {
                    adapter = CountriesAdapter(CoronD)
                    val layoutManager = LinearLayoutManager(this@CountriesActivity)
                    mRecyclerView.layoutManager = layoutManager
                    mRecyclerView.adapter = adapter

                    dialog.dismiss()

                    editText.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(p0: Editable?) {

                            filterList(p0.toString(), CoronD)
                        }

                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }

                    })


                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }


        })


    }


    private fun filterList(filterItem: String, coronaData: CoronaData) {
        displayList = coronaData
        var tempList: MutableList<CoronaDataItem> = ArrayList()

        for (d in displayList) {
            var tester = d.countryInfo.iso2
            val countryPicker: Locale = Locale.Builder().setRegion(tester).build()

            val langArabic: Locale = Locale.Builder().setLanguage("ar").build()
            d.translated = countryPicker.getDisplayCountry(langArabic)
            if (filterItem in d.translated.toLowerCase(Locale.getDefault()) || filterItem in d.translated || filterItem in d.translated.toUpperCase(

                    Locale.getDefault()

                )
            ) {

                tempList.add(d)
            } else if (filterItem in d.country.toLowerCase(Locale.getDefault()) || filterItem in d.country || filterItem in d.country.toUpperCase(
                    Locale.getDefault()
                )
            ) {

                tempList.add(d)
            }
        }

        adapter.updateList(tempList)


    }

    override fun onBackPressed() {

        finish()
        super.onBackPressed()
    }


}
