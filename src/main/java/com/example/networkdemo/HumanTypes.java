package com.example.networkdemo;

public enum HumanTypes implements Typess{
    //options that the user can request to do
    CREATE_SOLOGAME ("REQUEST: Create new single game"),
    CREATE_MULTIGAME("REQUEST: Create new multi game"),
    SEND_NAME ("REQUEST: Send human player's name"),
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
    ROOM_ADDED ("New room added"),
    ROOM_UPDATED("Room updated"),
    JOIN_SUCCESS("You joined the game"),
    JOIN_FAIL("This room is occupied"),
    MOVE_MADE ("Current move"),
    PLAYER_TURN("Player's turn"),
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
