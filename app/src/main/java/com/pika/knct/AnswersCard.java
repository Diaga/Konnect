package com.pika.knct;

public class AnswersCard {

    int userImage;
    String userName;
    String userInstitue;

    public AnswersCard(int userImage, String userName, String userInstitue) {
        this.userImage = userImage;
        this.userName = userName;
        this.userInstitue = userInstitue;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserInstitue() {
        return userInstitue;
    }

    public void setUserInstitue(String userInstitue) {
        this.userInstitue = userInstitue;
    }
}
