package controller;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import model.MyFile;

import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Controller {

    @FXML
    TableView<MyFile> tableFile;
    @FXML
    TableColumn<MyFile, NamePlusImg> colName;
    @FXML
    TableColumn<MyFile, String> colDateModif;
    @FXML
    TableColumn<MyFile, String> colSize;


    MyFile myFile = new MyFile();
    ObservableList list = myFile.initFileList("f:\\");

    @FXML
    private void initialize(){
        //NamePlusImg namePlusImg = new NamePlusImg(myFile.getNamePlusImg(), jswingIconToImage(getJSwingIconFromFileSystem(myFile.fi)));
        colName.setCellValueFactory(new PropertyValueFactory<MyFile, NamePlusImg>("name"));
        /* @Override
         public ListCell<String> call(ListView<String> list) {
             return new AttachmentListCell();
         }*/
        colName.setCellFactory(param -> new AttachmentListCell());

        //colName.graphicProperty().setValue(new ImageView(jswingIconToImage(getJSwingIconFromFileSystem(myFile.fi))));

        colDateModif.setCellValueFactory(new PropertyValueFactory<MyFile, String>("dateModif"));
        colSize.setCellValueFactory(new PropertyValueFactory<MyFile, String>("size"));
        tableFile.setItems(list);
    }


    /* list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
        @Override
        public ListCell<String> call(ListView<String> list) {
            return new ListViewCellFactory2.AttachmentListCell();
        }
    });*/


    public static class AttachmentListCell extends TableCell<MyFile, NamePlusImg> {

        MyFile myF = new MyFile();
        @Override
        public void updateItem(NamePlusImg item, boolean empty) {
            super.updateItem((item), empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                //Image fxImage = getFileIcon(item);
                Image fxImage = item.imageFile;
                //Image fxImage = jswingIconToImage(getJSwingIconFromFileSystem(item.fi));
                ImageView imageView = new ImageView(fxImage);
                setGraphic(imageView);
                setText(item.nameFile);
            }
        }
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

   /* static HashMap<String, Image> mapOfFileExtToSmallIcon = new HashMap<String, Image>();

    private static String getFileExt(String fname) {
        String ext = ".";
        int p = fname.lastIndexOf('.');
        if (p >= 0) {
            ext = fname.substring(p);
        }
        return ext.toLowerCase();
    }


    public static Image getFileIcon(String fname) {
        final String ext = getFileExt(fname);

        Image fileIcon = mapOfFileExtToSmallIcon.get(ext);
        if (fileIcon == null) {

            javax.swing.Icon jswingIcon = null;

            File file = new File(fname);
            if (file.exists()) {
                jswingIcon = getJSwingIconFromFileSystem(file);
            }
            else {
                File tempFile = null;
                try {
                    tempFile = File.createTempFile("icon", ext);
                    jswingIcon = getJSwingIconFromFileSystem(tempFile);
                }
                catch (IOException ignored) {
                    // Cannot create temporary file.
                }
                finally {
                    if (tempFile != null) tempFile.delete();
                }
            }

            if (jswingIcon != null) {
                fileIcon = jswingIconToImage(jswingIcon);
                mapOfFileExtToSmallIcon.put(ext, fileIcon);
            }
        }

        return fileIcon;
    }*/
}
