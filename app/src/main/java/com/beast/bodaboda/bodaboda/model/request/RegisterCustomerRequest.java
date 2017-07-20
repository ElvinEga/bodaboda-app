package com.beast.bodaboda.bodaboda.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterCustomerRequest {

@SerializedName("username")
@Expose
private String username;
@SerializedName("password")
@Expose
private String password;
@SerializedName("userId")
@Expose
private String userId;
@SerializedName("deviceId")
@Expose
private String deviceId;
@SerializedName("fullname")
@Expose
private String fullname;
@SerializedName("phone")
@Expose
private String phone;

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getDeviceId() {
return deviceId;
}

public void setDeviceId(String deviceId) {
this.deviceId = deviceId;
}

public String getFullname() {
return fullname;
}

public void setFullname(String fullname) {
this.fullname = fullname;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

}
