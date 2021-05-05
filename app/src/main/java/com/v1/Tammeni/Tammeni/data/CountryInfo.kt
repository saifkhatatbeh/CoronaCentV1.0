package com.v1.implementaion.data


import com.google.gson.annotations.SerializedName

data class CountryInfo(
    val flag: String,
    @SerializedName("_id")
    val id: Int,
    val iso2: String

)