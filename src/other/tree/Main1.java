package other.tree;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.SimpleFileTreeItem;

import static controller.TableController.getJSwingIconFromFileSystem;
import static controller.TableController.jswingIconToImage;

public class Main1 extends Application {



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
                setGraphic(imageView);
                setText(item.getName());
            }
        }
    }
    @Override
    public void start(Stage primaryStage) {
        try {

			/*
			 * Adding a TreeView to the very left of the application window.
			 */
            TreeView<File> fileView = new TreeView<File>(
                    new SimpleFileTreeItem(new File("C:\\")));
            fileView.setCellFactory(param -> new AttachmentListCell());

			/* Creating a SplitPane and adding the fileView. */
            SplitPane splitView = new SplitPane();
            splitView.getItems().add(fileView);
            //splitView.getItems().add(new HTMLEditor());

			/* Create a root node as BorderPane. */
            BorderPane root = new BorderPane();

			/* Adding the menus as well as the content pane to the root node. */
            //root.setTop(menus);
            root.setCenter(splitView);

			/*
			 * Setting the root node of a scene as well as the applications CSS
			 * file. CSS file has to be in same src directory as this class. The
			 * path is always relative.
			 */
            Scene scene = new Scene(root, 800, 600);
            /*scene.getStylesheets().add(
                    getClass().getResource("application.css").toExternalForm());*/

			/* Adding a scene to the stage. */
            primaryStage.setScene(scene);
            primaryStage.setTitle("FileRex");

			/* Lift the curtain :0). */
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
