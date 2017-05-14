package com.diegoromoli.littlehttpserver;

public class DirectoryHtmlDisplay {

    private String path;
    private String display;
    private String serverAddress;

    public DirectoryHtmlDisplay(String serverAddress, String path, String display) {
        if (serverAddress == null) {
            throw new IllegalArgumentException("Server address cannot be null");
        }if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        if (display == null) {
            throw new IllegalArgumentException("Display cannot be null");
        }
        this.serverAddress = serverAddress;
        this.path = path;
        this.display = display;
    }

    public String toHtmlString() {
        return "<a href='" + serverAddress + path + "'>" + display + "</a>";
    }

    public String getDisplay() {
        return display;
    }

    public String getPath() {
        return path;
    }

}
