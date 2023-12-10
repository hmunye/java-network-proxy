package server;

import utils.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyServer {

    /**
     * Main method to start the server.
     *
     * @param args Command line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        // Creating a fixed thread pool to handle multiple client connections
        ExecutorService executor = Executors.newFixedThreadPool(10); // Adjust based on expected load

        // Try-with-resources to auto-close the ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(60000)) {
            System.out.println("Server is listening on port 60000");

            // Continuously listen for client connections
            while (true) {
                // Accepting a client connection
                Socket clientSocket = serverSocket.accept();

                // Log the acceptance of a new client connection
                System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

                // Submitting the client handling to a separate thread
                executor.submit(() -> {
                    System.out.println("Processing connection from " + clientSocket.getInetAddress().getHostAddress());
                    new ClientHandler(clientSocket).run();
                });
            }
        } catch (IOException e) {
            // Logging an error message if the server fails to listen on the specified port
            System.out.println("Could not listen on port 60000");
            e.printStackTrace();
        }
    }
}
