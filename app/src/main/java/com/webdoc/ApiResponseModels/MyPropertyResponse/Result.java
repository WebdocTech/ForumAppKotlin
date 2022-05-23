
package com.webdoc.ApiResponseModels.MyPropertyResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Result {

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("myPropertyDetails")
    @Expose
    private List<MyPropertyDetail> myPropertyDetails = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<MyPropertyDetail> getMyPropertyDetails() {
        return myPropertyDetails;
    }

    public void setMyPropertyDetails(List<MyPropertyDetail> myPropertyDetails) {
        this.myPropertyDetails = myPropertyDetails;
    }

}
