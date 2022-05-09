package com.webdoc.ModelClasses

class PriceCategories {
    var pricetitle: String
    var pricesubCatId: String? = null

    constructor(title: String, pricesubCatId: String?) {
        this.pricetitle = title
        this.pricesubCatId = pricesubCatId
    }

    constructor(title: String) {
        this.pricetitle = title
    }
}