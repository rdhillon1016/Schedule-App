package ui;

import exceptions.TaskOverlapException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Day;
import model.ScheduleApp;
import model.Task;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddTaskPageController {

    public DatePicker datePicker;
    private ObservableList<String> hours = FXCollections.observableArrayList
            ("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");

    private ObservableList<String> minutes = FXCollections.observableArrayList(
            "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");
    public ChoiceBox<String> startTimeHourButton;
    public ChoiceBox<String> startTimeMinuteButton;
    public ChoiceBox<String> endTimeHourButton;
    public ChoiceBox<String> endTimeMinuteButton;
    public TextArea taskDescription;
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    public void handleBackButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void handleAddTaskButton(ActionEvent event) throws ParseException, IOException {
        if ((taskDescription.getText() == null
                || taskDescription.getText().trim().isEmpty())
                || (datePicker.getValue() == null)
                ){
            ErrorWindow.show();
        } else {
            if (checkIfEndTimeBefore()){
                ErrorWindow.show();
            } else {
                try {
                    constructTask();
                } catch (TaskOverlapException e) {
                    System.out.println("Task Overlap");
                    showTaskOverlapWindow();
                }
                finally {
                handleBackButton(event);
                }
            }
        }
    }

    private Date setupStartTime() throws ParseException {
        return sdf2.parse(startTimeHourButton.getValue() + ":" + startTimeMinuteButton.getValue());
    }

    private Date setupEndTime() throws ParseException {
        return sdf2.parse(endTimeHourButton.getValue() + ":" + endTimeMinuteButton.getValue());
    }

    private boolean checkIfEndTimeBefore() throws ParseException {
        return setupEndTime().before(setupStartTime());
    }

    private void constructTask() throws ParseException, TaskOverlapException {
        ScheduleApp sa = ScheduleApp.getInstance();
        Task t = new Task();
        t.setTaskDescription(taskDescription.getText());
        Day day = sa.getDay(sa.getSdf().parse(formatter.format(datePicker.getValue())));
        t.setDay(day);
        t.changeStartTaskHour(startTimeHourButton.getValue() + ":" + startTimeMinuteButton.getValue());
        t.changeEndTaskHour(endTimeHourButton.getValue() + ":" + endTimeMinuteButton.getValue());
        day.assignAllNumbers();
        day.checkTaskDatesOverlap(t);
    }

    private void showTaskOverlapWindow(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Error");
        stage.setMinWidth(200);

        Label label = new Label();
        label.setText("Two tasks scheduled at same time. Remove if needed.");

        Button retry = new Button("Continue");
        retry.setOnAction(e -> stage.close());

        HBox layout = new HBox(20);
        layout.getChildren().addAll(label, retry);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void initialize(){
        startTimeHourButton.setValue("12");
        startTimeHourButton.setItems(hours);
        endTimeHourButton.setValue("12");
        endTimeHourButton.setItems(hours);
        startTimeMinuteButton.setValue("00");
        startTimeMinuteButton.setItems(minutes);
        endTimeMinuteButton.setValue("00");
        endTimeMinuteButton.setItems(minutes);
    }
}
