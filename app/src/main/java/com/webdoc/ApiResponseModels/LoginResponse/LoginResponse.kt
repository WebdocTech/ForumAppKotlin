package com.webdoc.ApiResponseModels.LoginResponse

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class LoginResponse {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("result")
    @Expose
    var result: Result? = null
}