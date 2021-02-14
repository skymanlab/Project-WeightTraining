package com.skymanlab.weighttraining.management.user.data;

import androidx.compose.ui.semantics.ProgressBarRangeInfo;

import com.skymanlab.weighttraining.management.FitnessCenter.data.UserFitnessCenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    // constant
    public static final String UID = "uid";
    public static final String NUMBER = "number";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHOTO_URL = "photoUrl";
    public static final String IS_SAVED_BASE_EVENT = " isSavedBaseEvent";
    public static final String FITNESS_CENTER = "fitnessCenter";

    // instant variable
    private String uid;             // 0. uid
    private long number;
    private String name;            // 2. name
    private String email;           // 3. email
    private String photoUrl;        // 4. photoUr
    private boolean isSavedBaseEvent;
    private UserFitnessCenter userFitnessCenter;

    // method : getter, setter
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isSavedBaseEvent() {
        return isSavedBaseEvent;
    }

    public void setSavedBaseEvent(boolean savedBaseEvent) {
        isSavedBaseEvent = savedBaseEvent;
    }

    public UserFitnessCenter getUserFitnessCenter() {
        return userFitnessCenter;
    }

    public void setUserFitnessCenter(UserFitnessCenter userFitnessCenter) {
        this.userFitnessCenter = userFitnessCenter;
    }
}
