package com.example.networkdemo;

import java.io.Serializable;
import java.util.Vector;

public class RoomList implements Serializable  {
    private Vector<GameRoom> gameRoomList;

    public RoomList() {
        gameRoomList = new Vector<GameRoom>();
    }

    public Vector<GameRoom> getGameRoomList() { return gameRoomList;}

    public int size() { return gameRoomList.size(); }

    public void addToList(GameRoom currentRoom) { gameRoomList.add(currentRoom); }

    public void updatePlayer2InList(GameRoom updatedRoom) {
        for (int i = 0; i < gameRoomList.size(); i++)
        {
            if (gameRoomList.get(i).getRoomID().equals(updatedRoom.getRoomID()))
                gameRoomList.get(i).getPlayer2().setUserName(updatedRoom.getPlayer2().getUserName());
        }
    }
    public void printList() {
        for (int i = 0; i < gameRoomList.size(); i++) {
            GameRoom currentRoom = gameRoomList.get(i);
            System.out.println("ID : " + currentRoom.getRoomID());
            System.out.println("Player 1 : " + currentRoom.getPlayer1().getUserName());
            if (currentRoom.getPlayer2().getUserName().equals(""))
                System.out.println("Player 2 : empty");
            else
                System.out.println("Player 2 : " + currentRoom.getPlayer2().getUserName());
        }
    }

}

