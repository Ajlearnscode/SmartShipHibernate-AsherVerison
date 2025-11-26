package network_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main server class that listens for client connections.
 * This is the entry point - it starts everything up.
 */
public class SmartShipServer {
    public static void main(String[] args) {
        // Open a server socket on port 8000
        // try-with-resources automatically closes the socket when we're done
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("SmartShip Server started on port 8000...");
            System.out.println("Waiting for clients...");

            // Keep the server running forever
            // Every time a client connects, we handle them and then wait for the next one
            while (true) {
                // accept() pauses here until someone connects
                // Once they do, we get a socket to talk to them
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                // Create a handler to deal with this specific client
                ClientHandler handler = new ClientHandler(clientSocket);

                // Start a new thread so this client runs independently
                // This way we can handle multiple clients at once
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            // If the server crashes or can't start, print what went wrong
            e.printStackTrace();
        }
    }
}