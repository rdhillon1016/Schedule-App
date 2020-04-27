package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ScheduleApp;

import java.io.IOException;

public class main extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        window = primaryStage;
        window.setTitle("Schedule App");

        window.setOnCloseRequest(e -> {
            try {
                closeProgram();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        window.setScene(new Scene(root, 800, 500));
        window.show();
    }

    private void closeProgram() throws IOException {
        ScheduleApp sa = ScheduleApp.getInstance();
        sa.removeEmptyDays();
        sa.save();
        window.close();
    }
}
