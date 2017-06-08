package other;

import java.io.File;
/*from  w  w  w  .  j  ava2s .com*/
        import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
        import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
        import javafx.scene.layout.VBoxBuilder;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;

import static javafx.scene.control.ChoiceBoxBuilder.*;

public class FileCh extends Application {
    public static void main(String[] args) {
        launch(args);
    }

        ObservableList cursors = FXCollections.observableArrayList(
                Cursor.DEFAULT,
                Cursor.CROSSHAIR,
                Cursor.WAIT,
                Cursor.TEXT,
                Cursor.HAND,
                Cursor.MOVE,
                Cursor.N_RESIZE,
                Cursor.NE_RESIZE,
                Cursor.E_RESIZE,
                Cursor.SE_RESIZE,
                Cursor.S_RESIZE,
                Cursor.SW_RESIZE,
                Cursor.W_RESIZE,
                Cursor.NW_RESIZE,
                Cursor.NONE
        );
        @Override
        public void start(Stage stage) {
            ChoiceBox choiceBoxRef = create()
                    .items(cursors)
                    .build();

            VBox box = new VBox();
            box.getChildren().add(choiceBoxRef);
            final Scene scene = new Scene(box,300, 250);
            scene.setFill(null);
            stage.setScene(scene);
            stage.show();
            scene.cursorProperty().bind(choiceBoxRef.getSelectionModel()
                    .selectedItemProperty());

        }


    }


