package com.v1.Tammeni.data

class Leaderboard_data_items(
    val rank: Int,
    val text_username: String,
    val days_survived: Int,
    val country_image: String
){
    constructor(): this(0,"",0,"")
}
