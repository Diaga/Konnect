package com.pika.knct;

import android.graphics.Bitmap;

public class Querycard {

    String questions;
    String name;
    String location;
    Bitmap userimage;
    String answers;
    String time;

    public Querycard(String questions, String name, String location, Bitmap userimage, String answer, String time) {
        setQuestions(questions);
        this.name = name;
        this.location = location;
        this.userimage = userimage;
        this.answers = answer;
        this.time = time;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        int charlength = questions.length();
        if (charlength > 53 ){
            this.questions = questions.substring(0,50)+ "...";
        }else{
            this.questions=questions;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Bitmap getUserimage() {
        return userimage;
    }

    public void setUserimage(Bitmap userimage) {
        this.userimage = userimage;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
