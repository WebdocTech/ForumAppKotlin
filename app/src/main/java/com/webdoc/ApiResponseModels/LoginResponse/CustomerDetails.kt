package com.webdoc.ApiResponseModels.LoginResponse

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class CustomerDetails {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("mobileNumber")
    @Expose
    var mobileNumber: String? = null

    @SerializedName("profilePictureUrl")
    @Expose
    var profilePictureUrl: String? = null

    @SerializedName("balance")
    @Expose
    var balance: String? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null
}