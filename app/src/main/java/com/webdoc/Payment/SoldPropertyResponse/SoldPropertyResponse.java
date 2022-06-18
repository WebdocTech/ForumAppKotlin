
package com.webdoc.Payment.SoldPropertyResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SoldPropertyResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
