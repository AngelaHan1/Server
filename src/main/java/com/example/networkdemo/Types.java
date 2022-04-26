package com.example.networkdemo;

import java.io.Serializable;

public enum Types {

    CREATE_GAME ("Create a new game"), JOIN_GAME ("Join an exsisting game"),
    MAKE_MOVE ("Make a move"), QUIT ("Quit Game"),
    REMATCH_REQUEST ("Request rematch"), REMATCH_REJECT ("Reject rematch"),
    REMATCH_ACCEPT ("Accept Rematch"), SEND_MESSAGE ("Send a message");

    private String description;

    private Types(String description){

        this.description = description;
    }

    public String getDescription(){

        return description;
    }

}