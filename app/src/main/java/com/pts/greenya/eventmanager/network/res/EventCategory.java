package com.pts.greenya.eventmanager.network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventCategory {

    @SerializedName("categoryID")
    @Expose
    private int categoryID;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
