package com.v1.Tammeni.data.News


data class New(
    var ampWebUrl: String,
    var categories: List<String>,
    var cdnAmpWebUrl: String,
    var excerpt: String,
    var heat: Int,
    var images: List<Image>?,
    var locale: String,
    var path: String,
    var provider: Provider,
    var publishedDateTime: String,
    var tags: Any,
    var title: String,
    var topics: List<String>,
    var type: String,
    var updatedDateTime: Any,
    var webUrl: String
)