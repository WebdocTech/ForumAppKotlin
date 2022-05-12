package com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsDebitInquiryResult

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JsDebitInquiryResult {
    @SerializedName("processingCode")
    @Expose
    var processingCode: String? = null

    @SerializedName("merchantType")
    @Expose
    var merchantType: String? = null

    @SerializedName("traceNo")
    @Expose
    var traceNo: String? = null

    @SerializedName("companyName")
    @Expose
    var companyName: String? = null

    @SerializedName("dateTime")
    @Expose
    var dateTime: String? = null

    @SerializedName("responseCode")
    @Expose
    var responseCode: String? = null

    @SerializedName("responseDetails")
    @Expose
    var responseDetails: List<String>? = null

    @SerializedName("rrn")
    @Expose
    var rrn: String? = null

    @SerializedName("responseDescription")
    @Expose
    var responseDescription: String? = null
}