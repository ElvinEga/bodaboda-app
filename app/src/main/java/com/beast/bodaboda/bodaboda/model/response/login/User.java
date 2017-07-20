package com.beast.bodaboda.bodaboda.model.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("role")
@Expose
private Role role;
@SerializedName("expired")
@Expose
private String expired;
@SerializedName("ip")
@Expose
private String ip;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Role getRole() {
return role;
}

public void setRole(Role role) {
this.role = role;
}

public String getExpired() {
return expired;
}

public void setExpired(String expired) {
this.expired = expired;
}

public String getIp() {
return ip;
}

public void setIp(String ip) {
this.ip = ip;
}

}