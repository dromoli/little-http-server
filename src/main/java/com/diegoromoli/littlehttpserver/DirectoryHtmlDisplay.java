package com.diegoromoli.littlehttpserver;

public class DirectoryHtmlDisplay {

    private String path;
    private String display;

    public DirectoryHtmlDisplay(String path, String display) {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        if (display == null) {
            throw new IllegalArgumentException("Display cannot be null");
        }
        this.path = path;
        this.display = display;
    }

    public String toHtmlString() {
        return "<a href='http://localhost:8080/" + path + "'>" + display + "</a>";
    }

    public String getDisplay() {
        return display;
    }

    public String getPath() {
        return path;
    }

}
