package com.v1.Tammeni.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.v1.Tammeni.R
import com.v1.Tammeni.data.CoronaDataItem
import com.squareup.picasso.Picasso
import io.grpc.Context.current
import kotlinx.android.synthetic.main.country_row.view.*
import java.math.RoundingMode
import java.util.*
import java.util.concurrent.ThreadLocalRandom.current

var b = ""
var resourceid = 0
var population = 0.00
var total_cases = 0.00
var total_deaths = 0.00
var daily_cases = 0.00


class CountriesAdapter(var coronaData: List<CoronaDataItem>) :
    RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.country_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return coronaData.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val data = coronaData.get(position)

        population = data.population.toDouble() / 100.00
        total_cases = data.cases.toDouble() / 100.00
        total_deaths = data.deaths.toDouble() / 100.00
        daily_cases = data.todayCases.toDouble() / 100.00


        val rounderAverage_population =
            population.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
        val rounderAverage_total_cases =
            total_cases.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
        val rounderAverage_daily_cases =
            daily_cases.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
        val rounderAverage_total_deaths =
            total_deaths.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()


        var tester = data.countryInfo.iso2

        val countryDefault: Locale = Locale.Builder().setRegion(tester).build()

        var currentLanguage: String = Locale.getDefault().displayLanguage


        var langSelector: Locale = Locale.Builder().setLanguage("en").build()

        val current = holder.view.resources.configuration.locale.toString()


        if (current.contains("ar") || Locale.getDefault().language == "ar") {
            langSelector = Locale.Builder().setLanguage("ar").build()
        }

        if (currentLanguage.toLowerCase(Locale.ROOT).contains("العربية")) {
            langSelector = Locale.Builder().setLanguage("ar").build()

        }

        holder.view.txt_Country.text =
            countryDefault.getDisplayCountry(langSelector)

        holder.view.txt_Daily_Cases.text = rounderAverage(rounderAverage_daily_cases)

        holder.view.txt_Population.text = rounderAverage(rounderAverage_population)

        holder.view.txt_Total_Cases.text = rounderAverage(rounderAverage_total_cases)

        holder.view.txt_Deaths.text = rounderAverage(rounderAverage_total_deaths)

        val countryImage = holder.view.image_view
        Picasso.get().load(data.countryInfo.flag).into(countryImage)

    }

    fun updateList(list: MutableList<CoronaDataItem>) {
        coronaData = list
        notifyDataSetChanged()
    }

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
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)
