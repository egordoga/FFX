package other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.filechooser.FileSystemView;


public class LVCF2 extends Application {


    String path = "f:\\";
    File file = new File(path);
    ListView<File> list = new ListView<File>();
    ObservableList<File> data = initData(file);

    ObservableList<File> initData(File file){

        ObservableList<File> d = FXCollections.observableArrayList();
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile() || f.isDirectory()) d.add(f);
        }
        return d;
    }

    @Override
    public void start(Stage stage) {

        VBox box = new VBox();
        Scene scene = new Scene(box, 400, 600);
        stage.setScene(scene);
        stage.setTitle("ListViewSample");
        box.getChildren().addAll(list);
        VBox.setVgrow(list, Priority.ALWAYS);

        list.setItems(data);

        list.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {

            @Override
            public ListCell<File> call(ListView<File> list) {
                return new AttachmentListCell();
            }
        });
        initial(data);
        list.getSelectionModel().selectedItemProperty().addListener(
                (e, a, b) -> {
                    System.out.println(b);
                    file = b;
                    Runtime r = Runtime.getRuntime();
                    Process p = null;

                    try {
                        p = r.exec(String.valueOf(b));
                        p.waitFor();
                    }
                    catch (Exception ee) {
                        System.out.println(ee);
                    }

                });


        stage.show();
    }

    private void initial(ObservableList<File> data) {



    }

    public static class AttachmentListCell extends ListCell<File> {
        @Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                Image fxImage = jswingIconToImage(getJSwingIconFromFileSystem(item));
                ImageView imageView = new ImageView(fxImage);
                setText(item.getName());
                setGraphic(imageView);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }




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



    private static Image jswingIconToImage(javax.swing.Icon jswingIcon) {
        BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(), jswingIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}