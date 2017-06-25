package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.HashMap;
import java.util.Map;


public class TableController extends TableView {

    @FXML
    TreeView<File> paneTree;
    @FXML
    AnchorPane anchorTree;
    @FXML
    ChoiceBox<File> cbDrive;
    @FXML
    ToolBar tb;
    @FXML
    AnchorPane anchTab;


    private String path = "d:\\";
    private MyFile myFile = new MyFile();
    private Map<String, File> mapTab = new HashMap<>();
    private File dir = new File(path);
    private File file;
    private TabPane tabPane = new TabPane();


    @FXML
    private void initialize() {

        addTab(dir);
        initializeTabPane();
        initializeTree(dir);
        initializeChoiceBox();
    }

    private void initializeTree(File f) {

        paneTree = new TreeView<>(
                new SimpleFileTreeItem(f));
        paneTree.setCellFactory(param -> new TreeController.AttachmentListCell());
        paneTree.getRoot().setExpanded(true);
        anchorTree.getChildren().add(paneTree);
        paneTree.getSelectionModel().selectedItemProperty().addListener(
                (e, a, b) -> {
                    TableView<MyFile> tv = (TableView<MyFile>) tabPane.getSelectionModel().getSelectedItem().getContent();
                    tv.setItems(myFile.getList(b.getValue()));
                    Tab tab = tabPane.getSelectionModel().getSelectedItem();
                    tab.setId(String.valueOf(tab.hashCode()));
                    mapTab.put(tab.getId(), b.getValue());
                    tabName(b.getValue());

                });
    }


    private TableView<MyFile> addTable(File dir) {
        TableView<MyFile> tableFile = new TableView<>();
        TableColumn<MyFile, File> colName = new TableColumn<>("Имя");
        colName.setMinWidth(200);
        TableColumn<MyFile, String> colDateModif = new TableColumn<>("Дата");
        TableColumn<MyFile, String> colSize = new TableColumn<>("Размер");
        tableFile.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        colName.setCellValueFactory(new PropertyValueFactory<MyFile, File>("fi"));
        colName.setCellFactory(param -> new AttachmentTableCell());
        colDateModif.setCellValueFactory(new PropertyValueFactory<MyFile, String>("dateModif"));
        colSize.setCellValueFactory(new PropertyValueFactory<MyFile, String>("size"));
        tableFile.getColumns().addAll(colName, colDateModif, colSize);
        tableFile.setItems(myFile.getList(dir));




        File[] d = {dir};
        tableFile.setRowFactory(tv -> {
            TableRow<MyFile> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
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
                    } catch (Exception ee) {
                        System.out.println(ee);
                    }
                }
            });
            return row;
        });


        return tableFile;
    }

    private void initializeChoiceBox() {
        ObservableList<File> listDrive = FXCollections.observableArrayList();
        File[] roots = File.listRoots();
        Arrays.stream(roots).forEach(listDrive::add);
        cbDrive.setItems(listDrive);
        cbDrive.setAccessibleText(roots[0].getAbsolutePath());
        cbDrive.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends File> ov, File oldVal, File newVal) -> initializeTree(newVal));
    }

    private void initializeTabPane() {
        anchTab.getChildren().add(tabPane);
        AnchorPane.setBottomAnchor(tabPane, 5.0);
        AnchorPane.setLeftAnchor(tabPane, 5.0);
        AnchorPane.setRightAnchor(tabPane, 5.0);
        AnchorPane.setTopAnchor(tabPane, 5.0);
    }


    @FXML
    private void onClickBtnUp() {

        File dir = mapTab.get(tabPane.getSelectionModel().getSelectedItem().getId());
        if (dir.isDirectory()) {
            if (!(dir.getParent() == null)) {
                dir = dir.getParentFile();
                TableView<MyFile> tv = (TableView<MyFile>) tabPane.getSelectionModel().getSelectedItem().getContent();
                tv.setItems(myFile.getList(dir));
                tabName(dir);
                mapTab.put(tabPane.getSelectionModel().getSelectedItem().getId(), dir);
            }
        }
    }

    private void tabName(File dir) {
        if (!(dir.getParent() == null)) {
            tabPane.getSelectionModel().getSelectedItem().setText(dir.getName());
        } else {
            tabPane.getSelectionModel().getSelectedItem().setText(dir.getAbsolutePath());
        }
    }


    @FXML
    private void addTab(File dir) {
        Tab tab = new Tab();
        if (dir.getParent() == null) {
            tab.setText(dir.getAbsolutePath());
        } else {
            tab.setText(dir.getName());
        }
        tab.setId(String.valueOf(tab.hashCode()));
        mapTab.put(tab.getId(), dir);
        tab.setContent(addTable(dir));
        tabPane.getTabs().add(tab);
        tab.setOnClosed(event ->
        mapTab.remove(tab.getId())
        );
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