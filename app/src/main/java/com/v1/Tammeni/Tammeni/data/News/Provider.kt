package com.v1.Tammeni.data.News


data class Provider(
    var authors: Any,
    var domain: String,
    var images: List<Image>?,
    var name: String,
    var publishers: Any
)