package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.MyFile;
import model.MyTab;
import sample.SimpleFileTreeItem;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


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
    private int idTab = 1;
    private Map<String, File> mapTab = new HashMap<>();
    File dir = new File(path);
    File file;


    Tab tab = new Tab();
    TabPane tabPane = new TabPane();
    TableView<MyFile> tableFile;

    @FXML
    private void initialize(){

        addTab(dir);

        //tableFile = addTable(dir);
        initializeTabPane();
        //initializeTable();

        //tab.setContent(tableFile);

        initializeTree(dir);

        initializeChoiceBox();

        //tabListener();
    }

    private void initializeTree(File f) {

        paneTree = new TreeView<File>(
                new SimpleFileTreeItem(f));
        paneTree.setCellFactory(param -> new TreeController.AttachmentListCell());
        paneTree.getRoot().setExpanded(true);
        anchorTree.getChildren().add(paneTree);
        paneTree.getSelectionModel().selectedItemProperty().addListener(
                (e, a, b) -> {
                    //dir = b.getValue();
                    //tableFile.setItems(myFile.getList(dir));
                    TableView<MyFile> tv = (TableView<MyFile>) tabPane.getSelectionModel().getSelectedItem().getContent();
                    tv.setItems(myFile.getList(b.getValue()));
                    tabName(b.getValue());

                });
    }

    private void initializeTable() {





    }

    private TableView<MyFile> addTable(File dir) {
        TableView<MyFile> tableFile1 = new TableView<>();
        TableColumn<MyFile, File> colName = new TableColumn<>("Имя");
        colName.setMinWidth(200);
        TableColumn<MyFile, String> colDateModif = new TableColumn<>("Дата");
        TableColumn<MyFile, String> colSize = new TableColumn<>("Размер");
        tableFile1.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        colName.setCellValueFactory(new PropertyValueFactory<MyFile, File>("fi"));
        colName.setCellFactory(param -> new AttachmentTableCell());
        colDateModif.setCellValueFactory(new PropertyValueFactory<MyFile, String>("dateModif"));
        colSize.setCellValueFactory(new PropertyValueFactory<MyFile, String>("size"));
        tableFile1.getColumns().addAll(colName, colDateModif, colSize);
        tableFile1.setItems(myFile.getList(dir));

        System.out.println(dir.getParent() + "kkkkkkkk");

        File[] d = {dir};

        tableFile1.setRowFactory( tv -> {
            TableRow<MyFile> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    MyFile rowData = row.getItem();

                    if (rowData.getFi().isDirectory()) {
                        d[0] = rowData.getFi();
                    } else {
                        file = rowData.getFi();
                    }

                    try {
                        if (rowData.getFi().isFile()) {
                            Desktop dt = Desktop.getDesktop();
                            dt.open(file);
                        } else {
                            TableView<MyFile> tvMf = (TableView<MyFile>) tabPane.getSelectionModel().getSelectedItem().getContent();
                            tvMf.setItems(myFile.getList(d[0]));
                            tabName(d[0]);
                            mapTab.put(tabPane.getSelectionModel().getSelectedItem().getId(), d[0]);
                        }
                    }
                    catch (Exception ee) {
                        System.out.println(ee);
                    }
                }
            });
            return row ;
        });

        dir = d[0];
        System.out.println(dir.getParent() + "llllll");

        return tableFile1;
    }

    private void initializeChoiceBox(){
        ObservableList<File> listDrive = FXCollections.observableArrayList();
        File[] roots = File.listRoots();
        Arrays.stream(roots).forEach(listDrive::add);
        //listDrive.forEach(System.out::println);
        cbDrive.setItems(listDrive);
        cbDrive.setAccessibleText(roots[0].getAbsolutePath());
        cbDrive.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends File> ov, File oldVal, File newVal) ->initializeTree(newVal));
    }

    private void initializeTabPane(){

        //addTab(dir);
        anchTab.getChildren().add(tabPane);
        AnchorPane.setBottomAnchor(tabPane, 5.0);
        AnchorPane.setLeftAnchor(tabPane, 5.0);
        AnchorPane.setRightAnchor(tabPane, 5.0);
        AnchorPane.setTopAnchor(tabPane, 5.0);
    }

    /*private void addTab(TabPane tabPane) {

        Tab tab1 = new Tab(dir.getAbsolutePath());
        tab.setText(dir.getAbsolutePath());
        idTab++;
        mapTab.put(idTab, dir);
        tabPane.getTabs().add(tab1);
    }*/

    @FXML
    private void onClickBtnUp(){

       File dir = mapTab.get(tabPane.getSelectionModel().getSelectedItem().getId());
       if (dir.isDirectory()){
          if (!(dir.getParent() == null)) {
             // dir = new File(dir.getParent());
              dir = dir.getParentFile();
              TableView<MyFile> tv = (TableView<MyFile>) tabPane.getSelectionModel().getSelectedItem().getContent();
              tv.setItems(myFile.getList(dir));
              //addTable(dir);
              tabName(dir);
              mapTab.put(tabPane.getSelectionModel().getSelectedItem().getId(), dir);
          }
       }
    }

    private void tabName(File dir) {
        //File dir = mapTab.get(tabPane.getSelectionModel().getSelectedItem().getId());
        if (!(dir.getParent() == null)) {
            tabPane.getSelectionModel().getSelectedItem().setText(dir.getName());
        } else {
            tabPane.getSelectionModel().getSelectedItem().setText(dir.getAbsolutePath());
        }
    }



   /* @FXML
    public void addTab(File dir) {

        Tab tab = new Tab(dir.getName());
        tab.setId(String.valueOf(tab.hashCode()));
        mapTab.put(tab.getId(), dir);
        tab.setContent(addTable(dir));
        tabPane.getTabs().add(tab);
        System.out.println(tabPane.getSelectionModel().getSelectedItem());

         File prev = mapTab.get(tabPane.getSelectionModel().getSelectedItem().getId());
       File next = prev.getParentFile();
        TableView<MyFile> tv = (TableView<MyFile>) tabPane.getSelectionModel().getSelectedItem().getContent();
        tv.setItems(myFile.getList(next));
        mapTab.put(tabPane.getSelectionModel().getSelectedItem().getId(), next);
    }*/

    private void tabListener(){
       // tabPane.getSelectionModel().getSelectedItem().setText("HHHHHHH");

    }

    @FXML
    private void addTab(File dir) {
        Tab tab = new Tab();
        MyTab myTab = new MyTab(tab, dir);
        if (dir.getParent() == null){
            tab.setText(dir.getAbsolutePath());
        } else {
            tab.setText(dir.getName());
        }
        tab.setId(String.valueOf(tab.hashCode()));
        mapTab.put(tab.getId(), dir);
        tab.setContent(addTable(dir));
        tabPane.getTabs().add(tab);

        //return myTab;
    }

    public void clickAddTab(ActionEvent actionEvent) {
       addTab(mapTab.get(tabPane.getSelectionModel().getSelectedItem().getId()));
    }


    public static class AttachmentTableCell extends TableCell<MyFile, File> {
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