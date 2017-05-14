package com.diegoromoli.littlehttpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class LittleHttpServer {

    private static final String GET_REQUEST_PREFIX = "GET /";
    private static final String GET_REQUEST_POSTFIX = "HTTP/1.1";
    private static final String START_DIR = ".";
    private static final Integer MIN_SERVER_PORT = 1024;
    private static final Integer MAX_SERVER_PORT = 65535;
    private String serverCurrentDir;
    private Integer port;

    private static final Logger LOGGER = LoggerFactory.getLogger(LittleHttpServer.class);

    private LittleHttpServer(String startDir, Integer port) {
        this.serverCurrentDir = startDir;
        this.port = port;
    }

    private void go() {
        LOGGER.info("Starting server on port " + port);
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                try (
                        Socket socket = server.accept();
                        InputStreamReader reader = new InputStreamReader(socket.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream())) {
                    String line = bufferedReader.readLine();
                    LOGGER.debug("Received: " + line);
                    if (isValidRequest(line)) {
                        String str = processRequests(line);
                        writer.write(str);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.debug("Error: ", e);
            LOGGER.error("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private String getServerAddress() {
        return "http://localhost:" + port + "/";
    }

    private boolean isValidRequest(String line) {
        if (line == null)
            return false;
        line = line.toUpperCase();
        return line.startsWith(GET_REQUEST_PREFIX) && line.endsWith(GET_REQUEST_POSTFIX) && line.split(" ").length == 3;

    }

    private String processRequests(String request) throws IOException {
        String[] lineArray = request.split(" ");
        if (isValidPath(lineArray[1])) {
            serverCurrentDir = "." + lineArray[1];
        } else {
            return "HTTP/1.1 404 Not Found\r\n\r\n<html><font face='monospace'>Path Not Found<br/><br/><a href='" + getServerAddress() + "'>Back</a></font></html>";
        }
        File currentDir = new File(serverCurrentDir);
        if (currentDir.isDirectory()) {
            List<File> contents = Arrays.asList(currentDir.listFiles());
            String str = "<a href='" + getServerAddress() + "'>" +
                    ".</a><br/><a href='" + getServerAddress() + oneLevelUp(serverCurrentDir) + "'>..</a><br/>";
            str += contents.stream().sorted(new DirComparator()
            ).map(f -> (f.isDirectory() ? new DirectoryHtmlDisplay(getServerAddress(), f.getPath(), f.getName())
                    .toHtmlString() : new DirectoryHtmlDisplay(getServerAddress(), f.getPath(), f.getName())
                    .toHtmlString
                            ())).
                    collect(Collectors.joining("<br/>"));
            return "HTTP/1.1 200 OK\r\n\r\n<html><font face='monospace'>" + str + "</font></html>";
        } else {
            String fileContents = new String(Files.readAllBytes(Paths.get
                    (currentDir.toURI())));
            LOGGER.debug("File contents: " + fileContents);
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n" + fileContents;
        }
    }

    private String oneLevelUp(String serverCurrentDir) {
        return serverCurrentDir.substring(0, serverCurrentDir.lastIndexOf("/"));
    }

    private boolean isValidPath(String s) {
        File newDirectory = new File("." + s);
        return newDirectory.exists();
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(LittleHttpServer.class.getClassLoader().getResourceAsStream("littlehttpserver.properties"));
            Integer port = Integer.parseInt(properties.getProperty("port"));
            if (port < MIN_SERVER_PORT || port > MAX_SERVER_PORT) {
                throw new NumberFormatException("The 'port' property in littlehttpserver.properties must be an " +
                        "integer value between 0 and 65535.");
            }
            LittleHttpServer littleHttpServer = new LittleHttpServer(START_DIR, port);
            littleHttpServer.go();
        } catch (IOException ioe) {
            LOGGER.error("Error initialising server");
            System.exit(1);
        } catch (NumberFormatException nfe) {
            LOGGER.error("The 'port' property in littlehttpserver.properties must be an integer value between " +
                    MIN_SERVER_PORT + " and " + MAX_SERVER_PORT + ".");
            System.exit(1);
        }


    }
}
