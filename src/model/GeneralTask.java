package model;

import exceptions.InvalidDayException;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public abstract class GeneralTask extends SavingLineMaker {
    protected String taskDescription;
    protected int numberInList;
    protected Scanner sca;
    protected Date currentDay;
    protected Day day;

    public GeneralTask(){
        taskDescription = null;
        currentDay = new Date();
        numberInList = 0;
        sca = new Scanner(System.in);
        day = null;
    }

    // REQUIRES a string s in the form mm/dd/yyyy
    // MODIFIES this
    // EFFECTS converts String to taskDay
    public abstract void makeLines(List<String> lines);

    public abstract void setDay(Day d);

    public void provideAndChangeDesc(){
        System.out.println("Please provide a description of your task");
        setTaskDescription(sca.nextLine());
    }


    // REQUIRES a string s
    // MODIFIES this
    // EFFECTS changes taskDescription to s
    public void setTaskDescription(String s) {
        this.taskDescription = s;
    }

    // EFFECTS returns taskDescription
    public String getTaskDescription() {
        return taskDescription;
    }

    public Integer getNumber() {
        return numberInList;
    }

    // REQUIRES positive int g
    // MODIFIES this
    // EFFECTS changes numberInList to g
    public void setNumber(int g) {
        numberInList = g;
    }

    public void displayTaskNumber() {
        System.out.println("[" + Integer.toString(numberInList) + "]");
    }

    public void displayTaskDescription() {
        System.out.println(taskDescription);
    }

    public abstract void displayCombo();
}

