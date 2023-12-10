package client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ProxyClient {

    /**
     * Main method to start the client.
     *
     * @param args Command line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        // Server address and port number for the proxy server
        String serverAddress = "localhost";
        int serverPort = 60000;

        try {
            // Creating and binding a socket to the local port 60001
            Socket socket = new Socket();
            socket.bind(new InetSocketAddress(serverAddress, 60001)); // Bind to source port 60001
            socket.connect(new InetSocketAddress(serverAddress, serverPort));

            System.out.println("Successfully connected to the server at " + serverAddress + ":" + serverPort);

            // Try-with-resources to auto-close the PrintWriter and BufferedReader
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // Constructing and sending the request string to the server
                String specialFlag = "0xF0F0F0F0FEFEFEFE";
                String url = "https://www.mit.edu";
                String lengthString = String.format("%08d", url.length());
                String request = specialFlag + lengthString + url;
                out.println(request);

                System.out.println("Request sent to the server: " + url);

                // Reading the response from the server
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line).append("\n");
                }

                // Saving the received response to a local file
                try (FileWriter fileWriter = new FileWriter("webpage.html")) {
                    fileWriter.write(response.toString());
                    System.out.println("Webpage saved as webpage.html");
                }

            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " + serverAddress);
                e.printStackTrace();
            } finally {
                // Closing the socket
                socket.close();
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverAddress);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't open socket on port 60001");
            e.printStackTrace();
        }
    }
}
