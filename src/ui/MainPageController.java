package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainPageController {

    public VBox mainLayout;
    private ScheduleApp sa = ScheduleApp.getInstance();
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
    private Font font = new Font("Arial", 18);

    public void initialize() throws IOException, ParseException {
        sa.load();
        mainLayout.setSpacing(40);
        displaySchedule();
    }

    private void displaySchedule(){
        List<Day> days = sa.sortDays();
        for (Day d : days){
            VBox hbox = new VBox();
            hbox.setSpacing(20);

            // Date label
            Label dateLabel = new Label(d.getDateAsString());
            dateLabel.setFont(font);

            // Tasks
            VBox taskLayers = new VBox();
            for (Task t : d.getTasks()){
                String desc = sdf2.format(t.getStartTaskHour())
                        + " to " + sdf2.format(t.getEndTaskHour()) + " : " +
                        t.getTaskDescription() + ".";
                Label label = new Label(desc);
                taskLayers.getChildren().add(label);
            }
            Label label3 = new Label("");
            taskLayers.getChildren().add(label3);
            for (UntimedTask t : d.getUttasks()){
                String desc = t.getTaskDescription();
                Label label = new Label(desc);
                taskLayers.getChildren().add(label);
            }
            Label label4 = new Label("");
            taskLayers.getChildren().add(label4);
            for (Meal t : d.getMeals()){
                String desc = t.getTaskDescription() + ". " + t.getCalories() + " calories.";
                Label label = new Label(desc);
                taskLayers.getChildren().add(label);
            }
            hbox.getChildren().addAll(dateLabel, taskLayers);
            mainLayout.getChildren().add(hbox);
        }
    }

    public void handleRemoveTaskButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("RemoveTaskPage.fxml"));
        showNextScreen(parent, event);
    }

    public void handleAddTaskButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("AddTaskPage.fxml"));
        showNextScreen(parent, event);
    }

    public void handleAddUntimedTaskButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("AddUntimedPage.fxml"));
        showNextScreen(parent, event);
    }

    public void handleMealButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("AddMealPage.fxml"));
        showNextScreen(parent, event);
    }

    private void showNextScreen(Parent parent, ActionEvent event){
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
