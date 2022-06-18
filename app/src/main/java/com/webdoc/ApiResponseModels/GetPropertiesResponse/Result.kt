package com.webdoc.ApiResponseModels.GetPropertiesResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("discription")
    @Expose
    var discription: String? = null

    @SerializedName("pricePerSquareFoot")
    @Expose
    var pricePerSquareFoot: String? = null

    @SerializedName("pricePerSquareFootDiscount")
    @Expose
    var pricePerSquareFootDiscount: String? = null

    @SerializedName("areainSquareFoot")
    @Expose
    var areainSquareFoot: String? = null

    @SerializedName("areainlength")
    @Expose
    var areainlength: String? = null

    @SerializedName("discountInPercent")
    @Expose
    var discountInPercent: String? = null

    @SerializedName("downPayment")
    @Expose
    var downPayment: String? = null

    @SerializedName("quarterPayment")
    @Expose
    var quarterPayment: String? = null

    @SerializedName("totalQuarter")
    @Expose
    var totalQuarter: String? = null

    @SerializedName("totalAmount")
    @Expose
    var totalAmount: String? = null

    @SerializedName("discountedAmount")
    @Expose
    var discountedAmount: String? = null

    @SerializedName("features")
    @Expose
    var features: List<Feature>? = null

    @SerializedName("paymentandfloorplan")
    @Expose
    var paymentandfloorplan: String? = null

    @SerializedName("projectName")
    @Expose
    var projectName: String? = null

    @SerializedName("projectCompany")
    @Expose
    var projectCompany: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("propertyImageList")
    @Expose
    var propertyImageList: List<Any>? = null
}