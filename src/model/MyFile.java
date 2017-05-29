package model;

import controller.NamePlusImg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;

public class MyFile {
    //private NamePlusImg namePlusImg;
    private long dateModif;
    private long size;
    public File fi;

    public MyFile(NamePlusImg name, long dateModif, long size, File fi) {
        //this.namePlusImg = name;
        this.dateModif = dateModif;
        this.size = size;
        this.fi = fi;
    }

    public MyFile() {
    }

    public NamePlusImg getNamePlusImg(File f) {
        return new NamePlusImg(f.getName(), jswingIconToImage(getJSwingIconFromFileSystem(f)));
    }

    public File getFi() {
        return fi;
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
            if (f.isFile() || f.isDirectory()) {
                list.add(new MyFile(getNamePlusImg(f), f.lastModified(), f.length()/1024, f));
            /*} else listFile.add(new MyFile(new NamePlusImg(f.getName(), jswingIconToImage(getJSwingIconFromFileSystem(f))), f.lastModified(), f.length()/1024, f));*/
        }}
        //list.addAll(listDir);
        //list.addAll(listFile);

        return list;
    }


    private static Image jswingIconToImage(javax.swing.Icon jswingIcon) {
        BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(), jswingIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    private static javax.swing.Icon getJSwingIconFromFileSystem(File file) {

        FileSystemView view = FileSystemView.getFileSystemView();
        javax.swing.Icon icon = view.getSystemIcon(file);

        return icon;
    }
}
