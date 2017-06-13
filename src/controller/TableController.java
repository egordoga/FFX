package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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

   /* @FXML
    TableView<MyFile> tableFile;*/
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
    /*@FXML
    Tab tab;*/
    @FXML
    AnchorPane anchTab;


    String path = "d:\\";
    private MyFile myFile = new MyFile();
    File dir = new File(path);
    File file;


    Tab tab = new Tab();
    TabPane tabPane = new TabPane();
    TableView<MyFile> tableFile = addTable();

    @FXML
    private void initialize(){

        initializeTabPane();
        initializeTable();

        tab.setContent(tableFile);

        initializeTree(dir);

        initializeChoiceBox();
    }

    private void initializeTree(File f) {

        paneTree = new TreeView<File>(
                new SimpleFileTreeItem(f));
        paneTree.setCellFactory(param -> new TreeController.AttachmentListCell());
        paneTree.getRoot().setExpanded(true);
        anchorTree.getChildren().add(paneTree);
        paneTree.getSelectionModel().selectedItemProperty().addListener(
                (e, a, b) -> {
                    System.out.println(b);
                    dir = b.getValue();
                    tableFile.setItems(myFile.getList(dir));
                    tab.setText(dir.getName());

                });
    }

    private void initializeTable() {




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
                            tab.setText(dir.getName());
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

    private TableView<MyFile> addTable() {
        TableView<MyFile> tableFile = new TableView<>();
        TableColumn<MyFile, File> colName = new TableColumn<>("Наименование");
        TableColumn<MyFile, String> colDateModif = new TableColumn<>("Дата");
        TableColumn<MyFile, String> colSize = new TableColumn<>("Размер");
        colName.setCellValueFactory(new PropertyValueFactory<MyFile, File>("fi"));
        colName.setCellFactory(param -> new AttachmentListCell());
        colDateModif.setCellValueFactory(new PropertyValueFactory<MyFile, String>("dateModif"));
        colSize.setCellValueFactory(new PropertyValueFactory<MyFile, String>("size"));
        tableFile.setItems(myFile.getList(dir));
        tab.setText(dir.getName());
        return tableFile;
    }

    private void initializeChoiceBox(){
        ObservableList<File> listDrive = FXCollections.observableArrayList();
        File[] roots = File.listRoots();
        Arrays.stream(roots).forEach(listDrive::add);
        listDrive.forEach(System.out::println);
        cbDrive.setItems(listDrive);
        System.out.println();
        System.out.println(cbDrive.getItems());

        cbDrive.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends File> ov, File oldVal, File newVal) ->{
                    System.out.println(newVal);
                    //dir = newVal;
                    //paneTree = new TreeView<File>(new SimpleFileTreeItem(dir));
                    //paneTree.setCellFactory(param -> new TreeController.AttachmentListCell());
                    initializeTree(newVal);
        }
        );
    }

    private void initializeTabPane(){

        addTab(tabPane);
        anchTab.getChildren().add(tabPane);
    }

    private void addTab(TabPane tabPane) {

        Tab tab1 = new Tab(dir.getAbsolutePath());
        tab.setText(dir.getAbsolutePath());
        //ObservableList<Tab> tabs = FXCollections.observableArrayList();
        tabPane.getTabs().addAll(tab, tab1);
    }

    @FXML
    private void onClickBtnUp(){
       if (dir.isDirectory()) dir = new File(dir.getParent());
        tableFile.setItems(myFile.getList(dir));
        tab.setText(dir.getName());
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