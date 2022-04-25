package com.example.networkdemo;

public enum HumanTypes implements Typess{

    CREATE_GAME ("Create a new game"), JOIN_GAME ("Join an existing game"),
    MAKE_MOVE ("Make a move"), QUIT ("Quit Game"),
    REMATCH_REQUEST ("Request rematch"), REMATCH_REJECT ("Reject rematch"),
    REMATCH_ACCEPT ("Accept Rematch"), SEND_MESSAGE ("Send a message"),
    GAME_CREATED("A new game is created"),
    MOVE_MADE ("Current move"),MOVE_REJECTED("Invalid move"), WINNER ("Winner"),
    TIE ("Game tied!"), REMATCH_ACCEPTED("Accept rematch, clear board & restart game"),
    REMATCH_REJECTED ("Rejected Rematch, clear board"), GAME_OVER ("Game over. Clear board");


    private String description;

    private HumanTypes(String description){

        this.description = description;
    }

    public String getDescription(){

        return description;
    }

}