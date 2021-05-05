package com.v1.Tammeni.data


import com.v1.implementaion.data.CountryInfo

data class CoronaDataItem(
    val active: Int,
    val cases: Int,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Int,
    val deaths: Int,
    val population: Int,
    val recovered: Int,
    val tests: Int,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val updated: Long,
    var translated: String

)