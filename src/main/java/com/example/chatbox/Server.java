package com.example.chatbox;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Vector;

import com.example.networkdemo.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.Objects;

public class Server extends Application {
    private int clientNo = 0;
    // vector to store active clients
    static Vector<ClientHandler> clientList = new Vector<>();
    TextArea ta = new TextArea();
    static Vector<GameRoom> gameRoomList = new Vector<>();

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Text area for displaying contents


        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage


        new Thread( () -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);

                Platform.runLater(() ->
                        ta.appendText("Server started at " + new Date() + '\n'));

                while (!serverSocket.isClosed())
                {
                    // Listen for a connection request
                    Socket socket = serverSocket.accept();

                    // increment client no for each new client
                    clientNo++;

                    Platform.runLater( () -> {
                        // Display the client number
                        ta.appendText("Starting thread for client " + clientNo + " at" + new Date() + '\n');

                        // Find the client's host name, and IP address
                        InetAddress inetAddress = socket.getInetAddress();
                        ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
                        ta.appendText("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");

                    });

                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                    // Give a name to client
                    String clientName = "client" + clientNo;
//                    String name = in.readUTF();
//                    clientName = name;

                    // Create a new handler obj for this client
                    ClientHandler currentClient = new ClientHandler(socket, clientName, in, out, clientNo);

                    // Add this client to the client list
                    clientList.add(currentClient);
                    System.out.println("Adding this client to the client list...");
//
//                    if (clientNo % 2 != 0)
//                        out.writeObject(new Message('X',HumanTypes.MULTIGAME_CREATED));
//                    else
//                        out.writeObject(new Message('O',HumanTypes.MULTIGAME_CREATED));

                    // Create and start a new thread for the connection
                    new Thread(currentClient).start();
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }


    class ClientHandler implements Runnable {
        private String clientName;
        private Socket socket;
        final ObjectInputStream input;
        final ObjectOutputStream out;
        private int clientNo;
        // Text area for displaying contents

        // constructor
        public ClientHandler(Socket s, String name, ObjectInputStream in, ObjectOutputStream out, int clientNo)
        {
            this.clientName = name;
            this.socket = s;
            this.input = in;
            this.out = out;
            this.clientNo = clientNo;
        }


        @Override
        public void run() {
            try {

                while(!socket.isClosed()){
                    try {
                        //get message from the client
                        Object message = input.readObject(); //sidenote, try readLine() if this doesnt work
                        Message text = (Message)message;
                        String type = String.valueOf(text.getType());

                        //Object response = new Message("Multi", HumanTypes.MULTIGAME_CREATED);

                        //send text back to clients
                        for(int i = 0; i < Server.clientList.size(); i++){

                            ClientHandler client = Server.clientList.get(i);
                            try {
                                System.out.println("out: " + text.getType().getDescription());
                                client.out.writeObject(message);
                            }
                            catch (SocketException missfire){
                                missfire.printStackTrace();
                            }

                        }


                        switch (type) {
                            case "CREATE_MULTIGAME" :
                                // create a new game room and assign creator to room 1 with token 'X'
                                GameRoom room = new GameRoom(clientNo, "multi");
                                gameRoomList.add(room);
                                System.out.println("Adding a new room to the room list...");
                                // only send to sender
                                this.out.writeObject(new Message(room, HumanTypes.MULTIGAME_CREATED));


                                //send new room to all clients (to update the room list that players can choose
                                for(int i = 0; i < Server.clientList.size(); i++){
                                    ClientHandler client = Server.clientList.get(i);
                                    try {
                                        System.out.println("Send new room " + room.getRoomID());
                                        client.out.writeObject(new Message(room.getRoomID(), HumanTypes.ROOM_ADDED));
                                    }
                                    catch (SocketException missfire){
                                        missfire.printStackTrace();
                                    }
                                }
                                break;
                            case "JOIN_GAME":  // this message was sent with the room_id player wanna join
                                String room_id = (String) text.getData();
                                for (int i = 0; i < Server.gameRoomList.size(); i++) {
                                    GameRoom currentRoom = Server.gameRoomList.get(i);
                                    // if room is found
                                    if (currentRoom.getRoomID().equals(room_id)) {
                                        // if room is not full (player 2 hasn't joined)
                                        if (currentRoom.getPlayer2().getUserName().equals("")) {
                                            currentRoom.setPlayer2(clientNo);
                                            System.out.println("Adding client " + clientNo + " to " + currentRoom.getRoomID());
                                            this.out.writeObject(new Message(currentRoom, HumanTypes.JOIN_SUCCESS));
                                        }
                                        else  // else if the room is full
                                            this.out.writeObject(new Message("full", HumanTypes.JOIN_FAIL));
                                    }
                                    else
                                        this.out.writeObject(new Message("room not found", HumanTypes.JOIN_FAIL));
                                        System.out.println("Room not found");
                                }
                                break;
                        }

                        System.out.println();

//                        Platform.runLater( () -> {
//                            // Display the client number
//                            //ta.appendText("text received from " + clientName +  ": " + text.getType().getDescription() + "\n");
//                        });
                    }
                    catch  (SocketException e){
                        System.out.println("Client disconnected");
                        //clientNo--;
                        clientList.remove(this);
                        break;
                    }
                    catch (ClassNotFoundException ex){
                        ex.printStackTrace();
                    }

                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



    

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}