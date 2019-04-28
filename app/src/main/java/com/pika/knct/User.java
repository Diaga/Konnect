package com.pika.knct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @ColumnInfo
    @NonNull
    private String id;

    @ColumnInfo
    @NonNull
    private String displayName;

    @ColumnInfo
    @NonNull
    private String email;

    @ColumnInfo
    @Nullable
    private String photoURI;

    @ColumnInfo
    @Nullable
    private String registeredOn;

    @ColumnInfo
    @NonNull
    private String signedIn;

    @ColumnInfo
    @Nullable
    private String institute;

    @ColumnInfo
    @Nullable
    private String city;

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getPhotoURI() {
        return photoURI;
    }

    @Nullable
    public String getRegisteredOn() {
        return registeredOn;
    }

    @NonNull
    public String getSignedIn() {
        return signedIn;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setDisplayName(@NonNull String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setPhotoURI(@Nullable String photoURI) {
        this.photoURI = photoURI;
    }

    public void setRegisteredOn(@Nullable String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public void setSignedIn(@NonNull String signedIn) {
        this.signedIn = signedIn;
    }

    @Nullable
    public String getInstitute() {
        return institute;
    }

    public void setInstitute(@Nullable String institute) {
        this.institute = institute;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    public void setCity(@Nullable String city) {
        this.city = city;
    }
}
