package com.beast.bodaboda.bodaboda.model.response;

/**
 * Created by beast on 7/19/17.
 */

import com.beast.bodaboda.bodaboda.model.response.registercustomer.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterCustomer {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
