package ui;

import exceptions.InvalidCaloriesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.Day;
import model.Meal;
import model.ScheduleApp;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class AddMealPageController {

    @FXML
    public javafx.scene.control.TextField mealDescription;
    public DatePicker datePicker;
    public javafx.scene.control.TextField caloriePicker;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void handleBackButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void handleMealButton(ActionEvent event) throws IOException, ParseException {
        if ((mealDescription.getText() == null
                || mealDescription.getText().trim().isEmpty())
                || (caloriePicker.getText() == null
                || caloriePicker.getText().trim().isEmpty())
                || (datePicker.getValue() == null)
                || isNotInt(caloriePicker.getText())
                ){
            ErrorWindow.show();
        } else {
        constructMeal();
        handleBackButton(event);
        }
    }

    private void constructMeal() throws ParseException {
        ScheduleApp sa = ScheduleApp.getInstance();
        Meal meal = new Meal();
        meal.setTaskDescription(mealDescription.getText());
        meal.setCalories(Integer.parseInt(caloriePicker.getText()));
        Day day = sa.getDay(sa.getSdf().parse(formatter.format(datePicker.getValue())));
        meal.setDay(day);
        day.assignAllNumbers();
    }

    private boolean isNotInt(String input){
        try {
            Integer.parseInt(input);
        } catch(NumberFormatException e){
            return true;
        }
        return false;
    }
}
