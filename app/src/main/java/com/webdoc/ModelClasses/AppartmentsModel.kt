package com.webdoc.ModelClasses

class AppartmentsModel {
    var appartmentimage = 0
    var appartmenttitle: String
    lateinit var appartmentId :String
    lateinit var appartmentPrice: String
    lateinit var appartmentDate: String
    lateinit var appartmentTime: String
    constructor(appartmentimage: Int, appartmenttitle: String, appartmentId: String,appartmentPrice:String,
                appartmentDate:String,appartmentTime:String) {
        this.appartmentimage = appartmentimage
        this.appartmenttitle = appartmenttitle
        this.appartmentId = appartmentId
        this.appartmentPrice = appartmentPrice
        this.appartmentDate = appartmentDate
        this.appartmentTime = appartmentTime
    }

    constructor(appartmenttitle: String) {
        this.appartmenttitle = appartmenttitle
    }

    fun getImage(): Int {
        return appartmentimage
    }

    fun setImage(appartmentimage: Int) {
        this.appartmentimage = appartmentimage
    }

    fun getTitle(): String? {
        return appartmenttitle
    }

    fun setTitle(appartmenttitle: String) {
        this.appartmenttitle = appartmenttitle
    }
    fun getId(): String? {
        return appartmentId
    }

    fun setId(appartmentId: String) {
        this.appartmentId = appartmentId
    }
    fun getPrice(): String? {
        return appartmentPrice
    }

    fun setPrice(appartmentPrice: String) {
        this.appartmentPrice = appartmentPrice
    }
    fun getDate(): String? {
        return appartmentDate
    }

    fun setDate(appartmentDate: String) {
        this.appartmentDate = appartmentDate
    }

    fun getTime(): String? {
        return appartmentTime
    }

    fun setTime(appartmentTime: String) {
        this.appartmentTime = appartmentTime
    }
}