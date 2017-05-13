package com.diegoromoli.littlehttpserver;

import java.io.File;
import java.util.Comparator;

public class DirComparator implements Comparator<File> {

    @Override
    public int compare(File file1, File file2) {
        if (file1.isDirectory() != file2.isDirectory()) {
            if (file1.isDirectory()) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return file1.getName().compareTo(file2.getName());
        }
    }
}
