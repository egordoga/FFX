package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MyFile;

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

    @FXML
    private void initialize(){
        colName.setCellValueFactory(new PropertyValueFactory<MyFile, String>("name"));
        tableFile.setItems(list);
    }
}
