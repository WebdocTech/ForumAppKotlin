
package com.webdoc.ApiResponseModels.MyPropertyResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MyPropertyDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("discription")
    @Expose
    private String discription;
    @SerializedName("pricePerSquareFoot")
    @Expose
    private String pricePerSquareFoot;
    @SerializedName("pricePerSquareFootDiscount")
    @Expose
    private String pricePerSquareFootDiscount;
    @SerializedName("areainSquareFoot")
    @Expose
    private String areainSquareFoot;
    @SerializedName("areainlength")
    @Expose
    private String areainlength;
    @SerializedName("paymentandfloorplan")
    @Expose
    private String paymentandfloorplan;
    @SerializedName("projectName")
    @Expose
    private String projectName;
    @SerializedName("projectCompany")
    @Expose
    private String projectCompany;
    @SerializedName("purchaseDate")
    @Expose
    private String purchaseDate;
    @SerializedName("nextPaymentDate")
    @Expose
    private String nextPaymentDate;
    @SerializedName("sellType")
    @Expose
    private String sellType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("totalPayment")
    @Expose
    private String totalPayment;
    @SerializedName("totalInstallment")
    @Expose
    private String totalInstallment;
    @SerializedName("paidInstallment")
    @Expose
    private String paidInstallment;
    @SerializedName("remainingInstallment")
    @Expose
    private String remainingInstallment;

    @SerializedName("totalRemaningAmount")
    @Expose
    private String totalRemaningAmount;

    @SerializedName("installmentAmount")
    @Expose
    private String installmentAmount;

    @SerializedName("totalPaidAmount")
    @Expose
    private String totalPaidAmount;

    @SerializedName("features")
    @Expose
    private List<Feature> features = null;
    @SerializedName("paymentDetails")
    @Expose
    private List<PaymentDetail> paymentDetails = null;
    @SerializedName("propertyImageList")
    @Expose
    private List<Object> propertyImageList = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTotalRemaningAmount() {
        return totalRemaningAmount;
    }

    public void setTotalRemaningAmount(String totalRemaningAmount) {
        this.totalRemaningAmount = totalRemaningAmount;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(String totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getPricePerSquareFoot() {
        return pricePerSquareFoot;
    }

    public void setPricePerSquareFoot(String pricePerSquareFoot) {
        this.pricePerSquareFoot = pricePerSquareFoot;
    }

    public String getPricePerSquareFootDiscount() {
        return pricePerSquareFootDiscount;
    }

    public void setPricePerSquareFootDiscount(String pricePerSquareFootDiscount) {
        this.pricePerSquareFootDiscount = pricePerSquareFootDiscount;
    }

    public String getAreainSquareFoot() {
        return areainSquareFoot;
    }

    public void setAreainSquareFoot(String areainSquareFoot) {
        this.areainSquareFoot = areainSquareFoot;
    }

    public String getAreainlength() {
        return areainlength;
    }

    public void setAreainlength(String areainlength) {
        this.areainlength = areainlength;
    }

    public String getPaymentandfloorplan() {
        return paymentandfloorplan;
    }

    public void setPaymentandfloorplan(String paymentandfloorplan) {
        this.paymentandfloorplan = paymentandfloorplan;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCompany() {
        return projectCompany;
    }

    public void setProjectCompany(String projectCompany) {
        this.projectCompany = projectCompany;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(String nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getTotalInstallment() {
        return totalInstallment;
    }

    public void setTotalInstallment(String totalInstallment) {
        this.totalInstallment = totalInstallment;
    }

    public String getPaidInstallment() {
        return paidInstallment;
    }

    public void setPaidInstallment(String paidInstallment) {
        this.paidInstallment = paidInstallment;
    }

    public String getRemainingInstallment() {
        return remainingInstallment;
    }

    public void setRemainingInstallment(String remainingInstallment) {
        this.remainingInstallment = remainingInstallment;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<PaymentDetail> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public List<Object> getPropertyImageList() {
        return propertyImageList;
    }

    public void setPropertyImageList(List<Object> propertyImageList) {
        this.propertyImageList = propertyImageList;
    }

}
