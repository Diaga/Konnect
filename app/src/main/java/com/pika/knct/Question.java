package com.pika.knct;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @NonNull
    private int id;

    @ColumnInfo
    @NonNull
    private String userId;

    @ColumnInfo
    @NonNull
    private String question;

    @ColumnInfo
    @NonNull
    private String subject;

    @ColumnInfo
    @NonNull
    private String postedOn;

    @ColumnInfo
    @NonNull
    private String tags;

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@NonNull String subject) {
        this.subject = subject;
    }

    @NonNull
    public String getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(@NonNull String postedOn) {
        this.postedOn = postedOn;
    }

    @NonNull
    public String getTags() {
        return tags;
    }

    public void setTags(@NonNull String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
