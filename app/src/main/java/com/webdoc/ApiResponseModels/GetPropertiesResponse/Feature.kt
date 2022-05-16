package com.webdoc.ApiResponseModels.GetPropertiesResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Feature {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("value")
    @Expose
    var value: String? = null
}