package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    /**
     * Constructor to initialize the ClientHandler with a client socket.
     *
     * @param socket The socket associated with the connected client.
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    /**
     * The main logic of handling a client's request.
     * This method reads the request, processes it, and returns the response.
     */
    @Override
    public void run() {
        // Try-with-resources to auto-close resources after use
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Reading the request line from the client
            String inputLine = in.readLine();
            System.out.println("Received request from client: " + inputLine);

            // Checking if the request line starts with the special flag
            if (inputLine.startsWith("0xF0F0F0F0FEFEFEFE")) {
                try {
                    // Extracting the length of the URL from the request
                    String lengthString = inputLine.substring(18, 26);
                    int urlLength = Integer.parseInt(lengthString);

                    // Extracting the URL from the request
                    String url = inputLine.substring(26, 26 + urlLength);

                    // Fetching webpage content and updating relative links to absolute
                    System.out.println("Processing URL: " + url);
                    String webpageContent = fetchWebpage(url);
                    webpageContent = HTMLUpdater.updateLinks(webpageContent, url);

                    // Sending the processed webpage content back to the client
                    out.println(webpageContent);
                    System.out.println("Response sent to client.");

                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    // Sending an error message back to the client in case of invalid request format
                    System.err.println("Invalid request format from client.");
                    out.println("Invalid request format");
                    e.printStackTrace();
                }
            } else {
                // Sending an error message back to the client for requests that don't start with the special flag
                System.err.println("Invalid request received from client.");
                out.println("Invalid request");
            }

        } catch (IOException e) {
            // Logging any IOExceptions encountered when handling the client connection
            System.out.println("Error handling client connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Fetches the webpage content from the given URL.
     *
     * @param urlString The URL from which to fetch the webpage.
     * @return The content of the fetched webpage as a String.
     */
    private String fetchWebpage(String urlString) {
        StringBuilder content = new StringBuilder();
        HttpURLConnection httpURLConnection = null;

        try {
            // Creating a URL object and opening a connection to it
            URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            // Checking the HTTP response code
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("Fetching " + urlString + " - Response Code: " + responseCode);

            // Reading the webpage content if response code is OK
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            // Handling exceptions during webpage fetching and logging the error
            System.out.println("Error fetching webpage: " + e.getMessage());
            content = new StringBuilder("Failed to fetch webpage");
            e.printStackTrace();
        } finally {
            // Ensuring the HttpURLConnection is closed after use
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return content.toString();
    }
}
