# Proxy Server Project

## Description
This Java project implements a forward proxy agent, capable of handling client requests for webpages, fetching them from the internet, and relaying the content back to the client. It utilizes multithreading to manage multiple client connections simultaneously and modifies HTML content to ensure proper rendering.

## Features
- **Multi-Threaded Server**: Handles multiple client requests concurrently.
- **Custom Protocol Handling**: Interprets requests with a special flag and URL.
- **Dynamic Webpage Fetching**: Fetches requested webpages and returns them to the client.
- **HTML Link Modification**: Converts relative URLs to absolute for correct webpage rendering.

## Prerequisites
- Java 17
- Maven
- IntelliJ IDEA (Recommended)

## Maven Dependency
This project uses Jsoup for HTML parsing and manipulation. The dependency is defined in the `pom.xml` file:

```xml
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.17.1</version> <!-- Ensure this matches the version you use -->
</dependency>
```

## Installation
Clone the repository:
```bash
git clone https://github.com/hmunye/java-network-proxy.git
```

## Usage

### Running the Server
1. Navigate to `src/main/java/server/ProxyServer.java`.
2. Run the `ProxyServer` class. The server starts on port 60000.

### Running the Client
1. Navigate to `src/main/java/client/ProxyClient.java`.
2. Modify `serverAddress` to match the server's IP (use "localhost" for local testing).
3. Run the `ProxyClient` class.


## Testing

### Localhost Test
1. Run both the server and client on the same machine.
2. Observe the console output for request handling and response status.

### LAN Test
1. Run the server on one machine and the client on another within the same LAN.
2. Ensure proper IP configuration in the client setup.
3. Observe the interaction between the server and client.

### SSH Test
1. Set up two accounts over an SSH connection. One account will run the server, and the other will run the client.
2. Use SSH to connect from the client account to the server account.
3. Run the server and client as per instructions and observe the interaction.

### Virtual Machine Test
1. Set up two Virtual Machines (VMs) on a single machine or separate machines.
2. On one VM, run the server application. On the other VM, run the client application.
3. Ensure network connectivity between the two VMs.
4. Observe the interaction between the server and client, ensuring proper request handling and response.
