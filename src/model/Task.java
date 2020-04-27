package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Task extends GeneralTask implements Comparable<Task> {
    private Date startTaskHour = new Date();
    private Date endTaskHour = new Date();
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
    private Scanner sc = new Scanner(System.in);

    public void displayTaskDate() {
        System.out.println(sdf2.format(startTaskHour) + " to " + sdf2.format(endTaskHour));
    }

    @Override
    public void setDay(Day d){
        this.day = d;
        d.addTask(this);
    }

    @Override
    public void displayCombo(){
        displayTaskNumber();
        displayTaskDate();
        displayTaskDescription();
    }

    @Override
    public void makeLines(List<String> lines) {
        List<String> lineContents = new ArrayList<>();
        lineContents.add("timed");
        lineContents.add(getNumber().toString());
        lineContents.add(getTaskDescription());
        lineContents.add(startHourToFormatString());
        lineContents.add(endHourToFormatString());
        lineContents.add(day.dayToFormatString());
        lines.add(constructLine(lineContents));
    }

    public String endHourToFormatString() {
        return sdf2.format(endTaskHour);
    }

    public String startHourToFormatString() {
        return sdf2.format(startTaskHour);
    }

    @Override
    public int compareTo(Task t) {
        return getStartTaskHour().compareTo(t.getStartTaskHour());
    }

    public Date getStartTaskHour(){
        return startTaskHour;
    }

    public Date getEndTaskHour(){
        return endTaskHour;
    }

    public Boolean checkTaskDateOverlap(Task n){
        return (checkHourOverlap(n.getStartTaskHour()) ||
                checkHourOverlap(n.getEndTaskHour())
        );
    }

    private boolean checkHourOverlap(Date taskHour) {
        return taskHour.before(getEndTaskHour())
                && taskHour.after(getStartTaskHour());
    }

    // REQUIRES a string s in the form HH:mm
    // MODIFIES this
    // EFFECTS converts String to startTaskHour
    public void changeStartTaskHour(String s) throws ParseException {
            startTaskHour = sdf2.parse(s);
    }

    // REQUIRES a string s in the form HH:mm
    // MODIFIES this
    // EFFECTS converts String to endTaskHour
    public void changeEndTaskHour(String s) throws ParseException {
            endTaskHour = sdf2.parse(s);
    }

    public void setAllInfo() throws ParseException {
        System.out.println("Please provide the start time of your task (HH:MM)");
        String startTime = sc.nextLine();
        changeStartTaskHour(startTime);
        System.out.println("Please provide the end time of your task (HH:MM)");
        String endTime = sc.nextLine();
        changeEndTaskHour(endTime);
        provideAndChangeDesc();
    }


}
