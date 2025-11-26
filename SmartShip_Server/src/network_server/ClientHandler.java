package network_server;

import java.io.*;
import java.net.Socket;

/**
 * This class handles one client connection.
 * Each client runs in its own thread.
 */
public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Set up streams so we can send and receive objects from the client
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

            // The client sends a string that tells us what action they want
            String action = (String) in.readObject();
            System.out.println("Action: " + action);

            // Look up the command that matches the action name
            Command command = CommandRegistry.getCommand(action);

            if (command != null) {
                // Run the command and send the result back to the client
                Object result = command.execute(in);
                out.writeObject(result);
                out.flush();
            } else {
                // If the action doesn't match anything, just send back null
                out.writeObject(null);
                out.flush();
            }

            // We're done with this client, so close the connection
            clientSocket.close();

        } catch (Exception e) {
            // Print any errors that happen while working with the client
            e.printStackTrace();
        }
    }
}

