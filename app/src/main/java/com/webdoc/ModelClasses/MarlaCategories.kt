package com.webdoc.ModelClasses

class MarlaCategories {
    var marlatitle: String
    var marlasubCatId: String? = null

    constructor(marlatitle: String, marlasubCatId: String?) {
        this.marlatitle = marlatitle
        this.marlasubCatId = marlasubCatId
    }

    constructor(marlatitle: String) {
        this.marlatitle = marlatitle
    }
}