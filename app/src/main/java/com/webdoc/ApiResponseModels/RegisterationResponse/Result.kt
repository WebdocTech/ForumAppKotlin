package com.webdoc.ApiResponseModels.RegisterationResponse

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Result {
    @SerializedName("responseCode")
    @Expose
    var responseCode: String? = null

    @SerializedName("responseMessage")
    @Expose
    var responseMessage: String? = null
}