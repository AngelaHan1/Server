package com.example.networkdemo;

public enum HumanTypes implements Typess{
    //options that the user can request to do
    CREATE_SOLOGAME ("REQUEST: Create new single game"),
    CREATE_MULTIGAME("REQUEST: Create new multi game"),
    JOIN_GAME ("REQUEST: Join game"),
    MAKE_MOVE ("REQUEST: Make move"),
    QUIT ("REQUEST: Quit Game"),
    REMATCH_REQUEST ("REQUEST: rematch"),
    REMATCH_REJECT ("REQUEST: reject rematch"),
    REMATCH_ACCEPT ("REQUEST: Accept Rematch"),
    SEND_MESSAGE ("REQUEST: Send a message"),

    //Responses back
    SOLOGAME_CREATED("New solo-game is created"),
    MULTIGAME_CREATED("New multi-game is created"),
    MOVE_MADE ("Current move"),
    MOVE_REJECTED("Invalid move"),
    WINNER ("Winner"),
    TIE ("Game tied!"),
    REMATCH_ACCEPTED("Accept rematch, clear board & restart game"),
    REMATCH_REJECTED ("Rejected Rematch, clear board"),
    GAME_OVER ("Game over. Clear board");


    private String description;

    private HumanTypes(String description){

        this.description = description;
    }

    public String getDescription(){

        return description;
    }

}
