package uk.co.alt236.easycursor.sampleapp.container;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JsonDataGsonModel {
    @SerializedName("id")
    private long id;

    @SerializedName("guid")
    private String guid;

    @SerializedName("balance")
    private String balance;

    @SerializedName("age")
    private int age;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    @SerializedName("about")
    private String about;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("tags")
    private List<String> tags;

    @SerializedName("customField")
    private String customField;

    public String getAbout() {
        return about;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getBalance() {
        return balance;
    }

    public String getCustomField() {
        return customField;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getGuid() {
        return guid;
    }

    public long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<String>();
        }

        return tags;
    }
}
