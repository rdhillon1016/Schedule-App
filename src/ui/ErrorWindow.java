package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorWindow {

    public static void show(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Error");
        stage.setMinWidth(200);

        Label label = new Label();
        label.setText("You have made an invalid input!");

        Button retry = new Button("Retry");
        retry.setOnAction(e -> stage.close());

        HBox layout = new HBox(20);
        layout.getChildren().addAll(label, retry);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
