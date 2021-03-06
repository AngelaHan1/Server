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

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    // Create a new handler obj for this client
                    ClientHandler currentClient = new ClientHandler(socket, "client" + clientNo, in, out);

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
        final DataInputStream input;
        final DataOutputStream out;
        // Text area for displaying contents


        // constructor
        public ClientHandler(Socket s, String name, DataInputStream in, DataOutputStream out)
        {
            this.clientName = name;
            this.socket = s;
            this.input = in;
            this.out = out;
        }

        @Override
        public void run() {
            try {
                // Create data input and output streams

//                DataInputStream inputFromClient = new DataInputStream(
//                        socket.getInputStream());
//
//
//                DataOutputStream outputToClient = new DataOutputStream(
//                        socket.getOutputStream());

                while(!socket.isClosed()){
                    try {
                        //get message from the client
                        String text = input.readUTF(); //sidenote, try readLine() if this doesnt work


                        String sendText = clientName +": " + text;

                        //send text back to clients
                        for(int i = 0; i < Server.clientList.size(); i++){

                            ClientHandler client = Server.clientList.get(i);
                            try {
                                client.out.writeUTF(sendText);
                            }
                            catch (SocketException missfire){
                                missfire.printStackTrace();
                            }
                            //i.out.writeUTF(sendText);
                            //break
                        }

                        System.out.println("Message Sent");

                        Platform.runLater( () -> {
                            // Display the client number
                            ta.appendText("text received from " + clientName +  ": " + text + "\n");


                        });
                    }
                    catch  (SocketException e){
                        System.out.println("Client disconnected");
                        //clientNo--;
                        clientList.remove(this);
                        break;
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