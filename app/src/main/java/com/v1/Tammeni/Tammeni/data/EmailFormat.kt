package com.v1.Tammeni.data

class EmailFormat(val email: String, val subject:String,val msg: String = ""){
    constructor(): this("","","")
}