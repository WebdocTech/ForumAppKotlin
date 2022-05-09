package com.webdoc.ModelClasses

class PropertyTypeModel {
    var propertytypetitle: String
    var propertyTypeCatId: String? = null

    constructor(propertytypetitle: String, propertyTypeCatId: String?) {
        this.propertytypetitle = propertytypetitle
        this.propertyTypeCatId = propertyTypeCatId
    }

    constructor(propertytypetitle: String) {
        this.propertytypetitle = propertytypetitle
    }
}