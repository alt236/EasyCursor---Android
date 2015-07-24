/*
 * ***************************************************************************
 * Copyright 2015 Alexandros Schillings
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ***************************************************************************
 *
 */

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
            tags = new ArrayList<>();
        }

        return tags;
    }
}
