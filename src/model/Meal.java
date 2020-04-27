package model;

import exceptions.InvalidCaloriesException;

import java.util.ArrayList;
import java.util.List;

public class Meal extends GeneralTask {

    private int calories;

    public Meal(){
        calories = 0;
    }

    @Override
    public void makeLines(List<String> lines) {
        List<String> lineContents = new ArrayList<>();
        lineContents.add("meal");
        lineContents.add(getNumber().toString());
        lineContents.add(getTaskDescription());
        lineContents.add(Integer.toString(calories));
        lineContents.add(day.dayToFormatString());
        lines.add(constructLine(lineContents));
    }

    @Override
    public void setDay(Day d){
        this.day = d;
        d.addMeal(this);
    }

    public void setCalories(int i) {
        calories = i;
    }

    public int getCalories(){
        return calories;
    }

    public void provideAndChangeMeal() {
        System.out.println("What meal would you like to add");
        setTaskDescription(sca.nextLine());
        System.out.println("How many calories is this meal?");
        try {
            setCalories(sca.nextInt());
        } catch (NumberFormatException e) {
            System.out.println("This is not a valid number!");
        }
    }

    public void displayCalories(){
        System.out.println(Integer.toString(calories));
    }

    public void displayCombo(){
        displayTaskNumber();
        displayTaskDescription();
        displayCalories();
    }
}
