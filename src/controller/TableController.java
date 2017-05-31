package controller;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.MyFile;
import sample.SimpleFileTreeItem;

import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;


public class TableController extends TableView{

    @FXML
    TableView<MyFile> tableFile;
    @FXML
    TableColumn<MyFile, File> colName;
    @FXML
    TableColumn<MyFile, String> colDateModif;
    @FXML
    TableColumn<MyFile, String> colSize;
    @FXML
    TreeView<File> paneTree;
    @FXML
    AnchorPane anchorTree;


    private MyFile myFile = new MyFile();

    @FXML
    private void initialize(){
        myFile.initFileListWithoutHidden("d:\\");
        myFile.listDir.addAll(myFile.listFile);
        colName.setCellValueFactory(new PropertyValueFactory<MyFile, File>("fi"));
        colName.setCellFactory(param -> new AttachmentListCell());
        colDateModif.setCellValueFactory(new PropertyValueFactory<MyFile, String>("dateModif"));
        colSize.setCellValueFactory(new PropertyValueFactory<MyFile, String>("size"));
        tableFile.setItems(myFile.listDir);
        tableFile.getItems().addAll(myFile.listFile);

        paneTree = new TreeView<File>(
                new SimpleFileTreeItem(new File("C:\\")));
        paneTree.setCellFactory(param -> new TreeController.AttachmentListCell());
        anchorTree.getChildren().add(paneTree);
    }


    public static class AttachmentListCell extends TableCell<MyFile, File> {
        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem((item), empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                Image fxImage = jswingIconToImage(getJSwingIconFromFileSystem(item));
                ImageView imageView = new ImageView(fxImage);
                setGraphic(imageView);
                setText(item.getName());
            }
        }
    }


    public static Image jswingIconToImage(javax.swing.Icon jswingIcon) {
        BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(), jswingIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public static javax.swing.Icon getJSwingIconFromFileSystem(File file) {

        FileSystemView view = FileSystemView.getFileSystemView();
        javax.swing.Icon icon = view.getSystemIcon(file);

        return icon;
    }



}