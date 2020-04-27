package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Day;
import model.ScheduleApp;
import model.UntimedTask;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class AddUntimedPageController {

    public DatePicker datePicker;
    public TextArea taskDescription;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void handleBackButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void handleTaskButton(ActionEvent event) throws IOException, ParseException {
        if ((taskDescription.getText() == null
                || taskDescription.getText().trim().isEmpty())
                || (datePicker.getValue() == null)
                ){
            ErrorWindow.show();
        } else {
            constructTask();
            handleBackButton(event);
        }
    }

    private void constructTask() throws ParseException {
        ScheduleApp sa = ScheduleApp.getInstance();
        UntimedTask ut = new UntimedTask();
        ut.setTaskDescription(taskDescription.getText());
        Day day = sa.getDay(sa.getSdf().parse(formatter.format(datePicker.getValue())));
        ut.setDay(day);
        day.assignAllNumbers();
    }

}
