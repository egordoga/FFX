package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.MyFile;
import sample.SimpleFileTreeItem;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;


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
    @FXML
    Button btnUp;
    @FXML
    ChoiceBox<File> cbDrive;
    @FXML
    ToolBar tb;


    private MyFile myFile = new MyFile();
    File dir = new File("f:\\");
    File file;

    @FXML
    private void initialize(){
        initializeTable();

        initializeTree();

        initializeChoiceBox();
    }

    private void initializeTree() {

        paneTree = new TreeView<File>(
                new SimpleFileTreeItem(dir));
        paneTree.setCellFactory(param -> new TreeController.AttachmentListCell());
        anchorTree.getChildren().add(paneTree);
        paneTree.getSelectionModel().selectedItemProperty().addListener(
                (e, a, b) -> {
                    System.out.println(b);
                    dir = b.getValue();
                    tableFile.setItems(myFile.getList(dir));

                });
    }

    private void initializeTable() {
        //myFile.initFileListWithoutHidden(file);
        //myFile.listDir.addAll(myFile.listFile);
        colName.setCellValueFactory(new PropertyValueFactory<MyFile, File>("fi"));
        colName.setCellFactory(param -> new AttachmentListCell());
        colDateModif.setCellValueFactory(new PropertyValueFactory<MyFile, String>("dateModif"));
        colSize.setCellValueFactory(new PropertyValueFactory<MyFile, String>("size"));
        tableFile.setItems(myFile.getList(dir));
        //tableFile.getItems().addAll(myFile.listFile);


        tableFile.setRowFactory( tv -> {
            TableRow<MyFile> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    MyFile rowData = row.getItem();

                    if (rowData.getFi().isDirectory()) {
                        dir = rowData.getFi();
                    } else {
                        file = rowData.getFi();
                    }
                    //Делайте, что требуется с элементом.
                    System.out.println(rowData.getFi().getName());

                    try {
                        if (rowData.getFi().isFile()) {
                            Desktop dt = Desktop.getDesktop();
                            dt.open(file);
                        } else {
                            tableFile.setItems(myFile.getList(dir));
                        }
                    }
                    catch (Exception ee) {
                        System.out.println(ee);
                    }
                }
            });
            return row ;
        });
    }

    private void initializeChoiceBox(){
        cbDrive = new ChoiceBox<>();
        ObservableList<File> listDrive = FXCollections.observableArrayList();
        File[] roots = File.listRoots();
        Arrays.stream(roots).forEach(listDrive::add);
        listDrive.forEach(System.out::println);
        //tb.getCh
        cbDrive.setItems(listDrive);
    }

    @FXML
    private void onClickBtnUp(){
       if (dir.isDirectory()) dir = new File(dir.getParent());
        tableFile.setItems(myFile.getList(dir));
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