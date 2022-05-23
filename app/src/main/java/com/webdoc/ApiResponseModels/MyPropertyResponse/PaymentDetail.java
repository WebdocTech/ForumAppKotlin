
package com.webdoc.ApiResponseModels.MyPropertyResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PaymentDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("debit")
    @Expose
    private String debit;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("modeOfPayment")
    @Expose
    private String modeOfPayment;
    @SerializedName("transectionId")
    @Expose
    private String transectionId;
    @SerializedName("addedBy")
    @Expose
    private String addedBy;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("balance")
    @Expose
    private String balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getTransectionId() {
        return transectionId;
    }

    public void setTransectionId(String transectionId) {
        this.transectionId = transectionId;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}
