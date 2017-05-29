package other;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;

public class LabelWithImages extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws InterruptedException {
        Scene scene = new Scene(new Group());
        stage.setTitle("Label With Image Sample");
        stage.setWidth(400);
        stage.setHeight(180);

        HBox hbox = new HBox();
        File file = new File("F:\\");
        //Replace the image you want to put up

        File[] files = file.listFiles();

        Label label = new Label();
        for (File f : files) {

            Image image = jswingIconToImage(getJSwingIconFromFileSystem(file));
            label.setGraphic(new ImageView(image));
            label.setText(f.getName());
            Thread.sleep(1000);

        hbox.setSpacing(10);
        hbox.getChildren().add((label));
        ((Group) scene.getRoot()).getChildren().add(hbox);

        stage.setScene(scene);
        stage.show();
    }}


    private static Image jswingIconToImage(javax.swing.Icon jswingIcon) {
        BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(), jswingIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    private static javax.swing.Icon getJSwingIconFromFileSystem(File file) {

        FileSystemView view = FileSystemView.getFileSystemView();
        javax.swing.Icon icon = view.getSystemIcon(file);

        return icon;
    }
}
