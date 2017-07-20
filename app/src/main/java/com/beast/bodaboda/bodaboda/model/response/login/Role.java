package com.beast.bodaboda.bodaboda.model.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Role {

@SerializedName("bitMask")
@Expose
private Integer bitMask;
@SerializedName("title")
@Expose
private String title;

public Integer getBitMask() {
return bitMask;
}

public void setBitMask(Integer bitMask) {
this.bitMask = bitMask;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

}