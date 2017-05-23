package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class MyFile {
    private String name;
    private long dateModif;
    private long size;
    public File fi;

    public MyFile(String name, long dateModif, long size, File fi) {
        this.name = name;
        this.dateModif = dateModif;
        this.size = size;
        this.fi = fi;
    }

    public MyFile() {
    }

    public String getName() {
        return name;
    }

    public long getDateModif() {
        return dateModif;
    }

    public long getSize() {
        return size;
    }

    public ObservableList<MyFile> initFileList(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        ObservableList<MyFile> list = FXCollections.observableArrayList();
        ObservableList<MyFile> listFile = FXCollections.observableArrayList();
        ObservableList<MyFile> listDir = FXCollections.observableArrayList();

        for (File f : files) {
            if (f.isDirectory()) {
                listDir.add(new MyFile(f.getName(), f.lastModified(), f.length()/1024, f));
            } else listFile.add(new MyFile(f.getName(), f.lastModified(), f.length()/1024, f));
        }
        list.addAll(listDir);
        list.addAll(listFile);

        return list;
    }
}
