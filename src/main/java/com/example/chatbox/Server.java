package com.example.chatbox;


import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Vector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {
    private int clientNo = 0;
    // vector to store active clients
    static Vector<ClientHandler> clientList = new Vector<>();
    TextArea ta = new TextArea();

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

                while (true)
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

                    // Create a new handler obj for this client
                    ClientHandler currentClient = new ClientHandler(socket, "client" + clientNo);

                    // Add this client to the client list
                    clientList.add(currentClient);
                    System.out.println("Adding this client to the client list...");

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
        // Text area for displaying contents


        // constructor
        public ClientHandler(Socket s, String name)
        {
            this.clientName = name;
            this.socket = s;
        }

        @Override
        public void run() {
            try {
                // Create data input and output streams
                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(
                        socket.getOutputStream());

                while(true){
                    InetAddress inetAddress = socket.getInetAddress();
                    //get message from the client
                    String text = inputFromClient.readUTF(); //sidenote, try readLine() if this doesnt work

                    //String testing = "test";
                    String sendText = clientName +": " + text;

                    //send text back to clients
                    outputToClient.writeUTF(sendText);

                    Platform.runLater( () -> {
                        // Display the client number
                        ta.appendText("text received from " + clientName +  ": " + text + "\n");


                    });

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