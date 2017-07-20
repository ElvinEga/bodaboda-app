package com.beast.bodaboda.bodaboda.model.response.registercustomer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("deviceId")
@Expose
private String deviceId;
@SerializedName("salt")
@Expose
private String salt;
@SerializedName("hash")
@Expose
private String hash;
@SerializedName("__v")
@Expose
private Integer v;
@SerializedName("role")
@Expose
private String role;
@SerializedName("username")
@Expose
private String username;
@SerializedName("id")
@Expose
private String id;

public String getDeviceId() {
return deviceId;
}

public void setDeviceId(String deviceId) {
this.deviceId = deviceId;
}

public String getSalt() {
return salt;
}

public void setSalt(String salt) {
this.salt = salt;
}

public String getHash() {
return hash;
}

public void setHash(String hash) {
this.hash = hash;
}

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
}

public String getRole() {
return role;
}

public void setRole(String role) {
this.role = role;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}