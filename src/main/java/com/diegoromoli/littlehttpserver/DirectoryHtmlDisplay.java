package com.diegoromoli.littlehttpserver;

import java.io.File;

public class DirectoryHtmlDisplay {

    private String path;
    private String display;
    private String serverAddress;

    public DirectoryHtmlDisplay(String serverAddress, File file) {
        if (serverAddress == null) {
            throw new IllegalArgumentException("Server address cannot be null");
        }if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.serverAddress = serverAddress;
        this.path = file.getPath();
        this.display = file.getName();
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
