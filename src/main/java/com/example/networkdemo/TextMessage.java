package com.example.networkdemo;

import java.io.Serializable;

public class TextMessage implements Serializable {
    String roomID;
    String text;
    String sender;

    TextMessage(String roomID, String text, String sender){
        this.roomID = roomID;
        this.text = text;
        this.sender = sender;

    }

    public void setText(String t){
        text = t;
    }

    public void setRoomID(String r){
        roomID = r;
    }

    public void setSender(String s){
        sender = s;
    }

    public String getText(){
        return text;
    }

    public String getRoomID(){
        return roomID;
    }

    public String getSender(){
        return sender;
    }
}
