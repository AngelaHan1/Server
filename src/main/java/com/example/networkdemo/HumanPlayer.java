package com.example.networkdemo;

import java.io.Serializable;

public class HumanPlayer implements Serializable {
    private String userName;
    private String room_id;
    private char token;


    //Constructor
    public HumanPlayer (String name){
        userName = name;
        String room_id  = " "; //always initialized as empty string, will get this value
        //once player joins a room to play.
        String token = " ";
    }

    //setters
    public void setUserName (String s){
        this.userName = s;
    }

    public void setRoom_id (String s){
        this.room_id = s;
    }

    public void setToken (char t){
        token = t;
    }

    //getters
    public String getUserName(){
        return userName;
    }

    public String getRoom_id(){
        return room_id;
    }

    public char getToken() {
        return token;
    }

}
