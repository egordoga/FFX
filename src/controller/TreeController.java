package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.SimpleFileTreeItem;

import java.io.File;

import static controller.TableController.getJSwingIconFromFileSystem;
import static controller.TableController.jswingIconToImage;


class TreeController extends TreeView {

    /*@FXML
    TreeView<File> paneTree;*/


    public static class AttachmentListCell extends TreeCell<File> {

        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem((item), empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                Image fxImage = jswingIconToImage(getJSwingIconFromFileSystem(item));
                ImageView imageView = new ImageView(fxImage);
                setText(item.getName());
                setGraphic(imageView);
            }
        }
    }


}
