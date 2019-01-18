package com.rootlol.chatrootlol.objMess;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bodyMess {

    @SerializedName("messege")
    @Expose
    private String messege;
    @SerializedName("login")
    @Expose
    private String login;

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}