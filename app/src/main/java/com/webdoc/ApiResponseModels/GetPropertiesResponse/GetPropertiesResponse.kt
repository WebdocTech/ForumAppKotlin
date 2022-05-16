package com.webdoc.ApiResponseModels.GetPropertiesResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetPropertiesResponse {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("result")
    @Expose
    var result: List<Result>? = null
}