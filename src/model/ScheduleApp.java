package model;

import exceptions.InvalidCaloriesException;
import exceptions.InvalidDayException;
import exceptions.InvalidOptionException;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ScheduleApp {
    private static ScheduleApp sa;
    private HashMap<Date, Day> mapOfDays;
    private Scanner sc;
    private SimpleDateFormat sdf;
    private SASaver sas;
    private SALoader sal;

    private ScheduleApp() {
        sc = new Scanner(System.in);
        mapOfDays = new HashMap<>();
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        sas = new SASaver();
        sal = new SALoader();
    }

    public static ScheduleApp getInstance(){
        if(sa == null){
            sa = new ScheduleApp();
        }
        return sa;
    }

    public SimpleDateFormat getSdf(){
        return sdf;
    }

    public void removeEmptyDays() {
        List<Day> days = new ArrayList<>();
        for (Day day : mapOfDays.values()) {
            days.add(day);
        }
        removeIfEmpty(days);
    }

    private void removeIfEmpty(List<Day> days) {
        for (Day day : days){
            if (day.getTasks().size() + day.getMeals().size() + day.getUttasks().size() == 0) {
                mapOfDays.remove(day.getDate());
            }
        }
    }

    public void removeADay() throws ParseException, InvalidDayException {
        System.out.println("Which date would you like to remove? (MM/DD/YYYY)");
        Date givenDate = sdf.parse(sc.nextLine());
        if (!checkIfDayExists(givenDate)){
            throw new InvalidDayException();
        }
        mapOfDays.remove(givenDate);
    }

    public Day removeOrChangePrompt() throws InvalidDayException, ParseException {
        System.out.println("Which date would you like to remove or change a task from?");
        Date givenDate = sdf.parse(sc.nextLine());
        if (!checkIfDayExists(givenDate)) {
            throw new InvalidDayException();
        }
        return mapOfDays.get(givenDate);
    }

    public Boolean checkIfDayExists(Date d) {
        return mapOfDays.get(d) != null;
    }

    public HashMap<Date, Day> getMapOfDays() {
        return mapOfDays;
    }

    public Day getDay(Date givenDate) {
        if (checkIfDayExists(givenDate)) {
            return mapOfDays.get(givenDate);
        } else {
            Day newDay = new Day(givenDate);
            mapOfDays.put(givenDate, newDay);
            return newDay;
        }
    }

    public void displayAllDays() {
        List<Day> days = new ArrayList<>();
        for (Day day : mapOfDays.values()) {
            days.add(day);
        }
        Collections.sort(days);
        for (Day day : days){
            day.displayAllTasks();
        }
    }

    public void load() throws ParseException, IOException {
        sal.load();
    }

    public void save() throws IOException {
        sas.save();
    }

    public List<Day> sortDays(){
        List<Day> days = new ArrayList<>();
        for (Day day : mapOfDays.values()) {
            days.add(day);
        }
        Collections.sort(days);
        return days;
    }


}



