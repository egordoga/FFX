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
//import other.ListViewCellFactory2;

import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Controller {

    @FXML
    TableView<MyFile> tableFile;
    @FXML
    TableColumn<MyFile, String> colName;
    @FXML
    TableColumn<MyFile, String> colDateModif;
    @FXML
    TableColumn<MyFile, String> colSize;


    MyFile myFile = new MyFile();
    ObservableList list = myFile.initFileList("F:\\Python\\Java");


   /* public static class AttachmentListCell extends TableCell<MyFile, String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                Image fxImage = getFileIcon(item);
                ImageView imageView = new ImageView(fxImage);
                setGraphic(imageView);
                setText(item);
            }
        }
    }


    static HashMap<String, Image> mapOfFileExtToSmallIcon = new HashMap<String, Image>();

    private static String getFileExt(String fname) {
        String ext = ".";
        int p = fname.lastIndexOf('.');
        if (p >= 0) {
            ext = fname.substring(p);
        }
        return ext.toLowerCase();
    }

    private static javax.swing.Icon getJSwingIconFromFileSystem(File file) {

        // Windows {
        FileSystemView view = FileSystemView.getFileSystemView();
        javax.swing.Icon icon = view.getSystemIcon(file);
        // }

        // OS X {
        //final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        //javax.swing.Icon icon = fc.getUI().getFileView(fc).getIcon(file);
        // }

        return icon;
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

    private static Image jswingIconToImage(javax.swing.Icon jswingIcon) {
        BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(), jswingIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }



    @FXML
    private void initialize(){

       /* colName.setCellFactory(new Callback<TableColumn<MyFile, String>, TableCell<MyFile, String>>() {


            @Override
            public TableCell<MyFile, String> call(TableColumn<MyFile, String> param) {
                return new AttachmentListCell();
            }*/

           /* @Override
            public ListCell<String> call(ListView<String> list) {
                return new ListViewCellFactory2.AttachmentListCell();
            }*/
      //  });

        colName.setCellValueFactory(new PropertyValueFactory<MyFile, String>("name"));

        FileSystemView view = FileSystemView.getFileSystemView();
        javax.swing.Icon icon = view.getSystemIcon(myFile.fi);

        colName.graphicProperty().setValue(new ImageView(jswingIconToImage(icon)));
        //label1.setGraphic(new ImageView(jswingIconToImage(icon)));



        colDateModif.setCellValueFactory(new PropertyValueFactory<MyFile, String>("dateModif"));
        colSize.setCellValueFactory(new PropertyValueFactory<MyFile, String>("size"));
        tableFile.setItems(list);
    }




}
