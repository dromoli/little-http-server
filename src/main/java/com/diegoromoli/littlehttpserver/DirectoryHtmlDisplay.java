package com.diegoromoli.littlehttpserver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DirectoryHtmlDisplay {

    private String path;
    private String display;
    private String serverAddress;
    private String lastModified;
    private String size;

    public DirectoryHtmlDisplay(String serverAddress, File file) {
        if (serverAddress == null) {
            throw new IllegalArgumentException("Server address cannot be null");
        }
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.serverAddress = serverAddress;
        this.path = file.getPath();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        this.display = file.getName() + (file.isDirectory() ? "/" : "");
        this.lastModified = sdf.format(new Date(file.lastModified()));
        this.size = String.valueOf(file.length());
    }

    public String toHtmlString() {
        return "<a href='" + serverAddress + path + "'>" + display + "</a>" + "\t" + lastModified + "\t" + size;
    }

}
