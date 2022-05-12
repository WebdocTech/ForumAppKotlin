package com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsDebitPaymentResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class JsDebitPaymentResponse {
    @SerializedName("commissionAmount")
    @Expose
    var commissionAmount: String? = null

    @SerializedName("companyName")
    @Expose
    var companyName: String? = null

    @SerializedName("dateTime")
    @Expose
    var dateTime: String? = null

    @SerializedName("merchantType")
    @Expose
    var merchantType: String? = null

    @SerializedName("processingCode")
    @Expose
    var processingCode: String? = null

    @SerializedName("responseCode")
    @Expose
    var responseCode: String? = null

    @SerializedName("responseDateTime")
    @Expose
    var responseDateTime: String? = null

    @SerializedName("responseDescription")
    @Expose
    var responseDescription: String? = null

    @SerializedName("responseDetails")
    @Expose
    var responseDetails: List<String>? = null

    @SerializedName("rrn")
    @Expose
    var rrn: String? = null

    @SerializedName("totalTransactionAmount")
    @Expose
    var totalTransactionAmount: String? = null

    @SerializedName("traceNo")
    @Expose
    var traceNo: String? = null

    @SerializedName("transactioId")
    @Expose
    var transactioId: String? = null

    @SerializedName("transactionAmount")
    @Expose
    var transactionAmount: String? = null
}
