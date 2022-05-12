package com.webdoc.webviewlibaray.JsBankWallet.ResponseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class JsBankAuthApi {
    @SerializedName("refresh_token_expires_in")
    @Expose
    var refreshTokenExpiresIn: String? = null

    @SerializedName("api_product_list")
    @Expose
    var apiProductList: String? = null

    @SerializedName("api_product_list_json")
    @Expose
    var apiProductListJson: List<String> = ArrayList()

    @SerializedName("organization_name")
    @Expose
    var organizationName: String? = null

    @SerializedName("developer.email")
    @Expose
    var developerEmail: String? = null

    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null

    @SerializedName("issued_at")
    @Expose
    var issuedAt: String? = null

    @SerializedName("client_id")
    @Expose
    var clientId: String? = null

    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null

    @SerializedName("application_name")
    @Expose
    var applicationName: String? = null

    @SerializedName("scope")
    @Expose
    var scope: String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn: String? = null

    @SerializedName("refresh_count")
    @Expose
    var refreshCount: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null
}