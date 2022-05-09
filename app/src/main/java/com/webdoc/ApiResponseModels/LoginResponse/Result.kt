package com.webdoc.ApiResponseModels.LoginResponse

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.webdoc.ApiResponseModels.LoginResponse.CustomerDetails

class Result {
    @SerializedName("responseCode")
    @Expose
    var responseCode: String? = null

    @SerializedName("responseMessage")
    @Expose
    var responseMessage: String? = null

    @SerializedName("customerDetails")
    @Expose
    var customerDetails: CustomerDetails? = null
}