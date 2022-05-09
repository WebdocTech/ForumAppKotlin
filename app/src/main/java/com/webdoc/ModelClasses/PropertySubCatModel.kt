package com.webdoc.ModelClasses

class PropertySubCatModel {
    var propertysubcatimage = 0
    var propertySubCattitle: String
    var propertySubCatId: String? = null

    constructor(propertysubcatimage: Int, propertySubCattitle: String, propertySubCatId: String) {
        this.propertysubcatimage = propertysubcatimage
        this.propertySubCattitle = propertySubCattitle
        this.propertySubCatId = propertySubCatId
    }

    constructor(propertySubCattitle: String) {
        this.propertySubCattitle = propertySubCattitle
    }

    fun getImage(): Int {
        return propertysubcatimage
    }

    fun setImage(propertysubcatimage: Int) {
        this.propertysubcatimage = propertysubcatimage
    }

    fun getTitle(): String? {
        return propertySubCattitle
    }

    fun setTitle(propertySubCattitle: String) {
        this.propertySubCattitle = propertySubCattitle
    }
}