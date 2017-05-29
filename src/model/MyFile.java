package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;

public class MyFile {
    private String dateModif;
    private String size;
    public File fi;

    public MyFile( String dateModif, String size, File fi) {
        this.dateModif = dateModif;
        this.size = size;
        this.fi = fi;
    }

    public MyFile() {
    }

    public ObservableList<MyFile> listFile = FXCollections.observableArrayList();
    public ObservableList<MyFile> listDir = FXCollections.observableArrayList();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");

    public File getFi() {
        return fi;
    }

    public String getDateModif() {
        return dateModif;
    }

    public String getSize() {
        return size;
    }

    public void initFileListWithoutHidden(String path){
        File file = new File(path);
        File[] files = file.listFiles();

        for (File f : files) {
            if (f.isFile() && !f.isHidden()) {
                listFile.add(new MyFile( sdf.format(f.lastModified()), f.length()/1024 + " Кб", f));
            } else if(f.isDirectory() && !f.isHidden()){
                listDir.add(new MyFile(sdf.format(f.lastModified()), "", f));
            }
        }
        //list.addAll(listDir);
        //list.addAll(listFile);


    }


    /*private static Image jswingIconToImage(javax.swing.Icon jswingIcon) {
        BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(), jswingIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    private static javax.swing.Icon getJSwingIconFromFileSystem(File file) {

        FileSystemView view = FileSystemView.getFileSystemView();
        javax.swing.Icon icon = view.getSystemIcon(file);

        return icon;
    }*/
}
