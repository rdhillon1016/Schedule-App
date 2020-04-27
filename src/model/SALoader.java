package model;

import exceptions.InvalidCaloriesException;
import exceptions.InvalidDayException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SALoader implements Loadable {

    public SALoader(){
    }

    public void load() throws IOException, ParseException {
        List<String> lines = Files.readAllLines(Paths.get("savefile.txt"));
        HashMap<Date, Day> mapOfDays = ScheduleApp.getInstance().getMapOfDays();
        SimpleDateFormat sdf = ScheduleApp.getInstance().getSdf();
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            if (partsOfLine.get(0).equals("timed")) {
                loadingForTimedTask(partsOfLine, mapOfDays, sdf);
            }
            if (partsOfLine.get(0).equals("untimed")) {
                loadingForUntimedTask(partsOfLine, mapOfDays, sdf);
            }
            if (partsOfLine.get(0).equals("meal")) {
                loadingForMeals(partsOfLine, mapOfDays, sdf);
            }
            if (partsOfLine.get(0).equals("day")) {
                loadingForDay(partsOfLine, mapOfDays, sdf);
            }
        }
        PrintWriter pw = new PrintWriter("savefile.txt", "UTF-8");
        pw.close();
    }
    private void loadingForDay(List<String> words, HashMap<Date, Day> mapOfDays, SimpleDateFormat sdf) throws ParseException {
        Date date = sdf.parse(words.get(1));
        Day d = new Day(date);
        mapOfDays.put(date, d);
    }
    private void loadingForUntimedTask(List<String> partsOfLine, HashMap<Date, Day> mapOfDays, SimpleDateFormat sdf) throws ParseException {
        UntimedTask t = new UntimedTask();
        t.setNumber(Integer.parseInt(partsOfLine.get(1)));
        String desc = partsOfLine.get(2);
        for(int i = 3; i <= partsOfLine.size() - 2; i++){
            desc += " " + partsOfLine.get(i);
        }
        t.setTaskDescription(desc);
        Date date = sdf.parse(partsOfLine.get(partsOfLine.size()-1));
        t.setDay(mapOfDays.get(date));
    }

    private void loadingForMeals(List<String> partsOfLine, HashMap<Date, Day> mapOfDays, SimpleDateFormat sdf) throws ParseException {
        Meal t = new Meal();
        t.setNumber(Integer.parseInt(partsOfLine.get(1)));
        String desc = partsOfLine.get(2);
        for(int i = 3; i <= partsOfLine.size() - 3; i++){
            desc += " " + partsOfLine.get(i);
        }
        t.setTaskDescription(desc);
        t.setCalories(Integer.parseInt(partsOfLine.get(partsOfLine.size()-2)));
        Date date = sdf.parse(partsOfLine.get(partsOfLine.size()-1));
        t.setDay(mapOfDays.get(date));
    }
    private void loadingForTimedTask(List<String> partsOfLine, HashMap<Date, Day> mapOfDays, SimpleDateFormat sdf) throws ParseException {
        Task t = new Task();
        t.setNumber(Integer.parseInt(partsOfLine.get(1)));
        String desc = partsOfLine.get(2);
        for(int i = 3; i <= partsOfLine.size() - 4; i++){
            desc += " " + partsOfLine.get(i);
        }
        t.setTaskDescription(desc);
        t.changeStartTaskHour(partsOfLine.get(partsOfLine.size()-3));
        t.changeEndTaskHour(partsOfLine.get(partsOfLine.size()-2));
        Date date = sdf.parse(partsOfLine.get(partsOfLine.size()-1));
        t.setDay(mapOfDays.get(date));
    }


    private static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }
}
