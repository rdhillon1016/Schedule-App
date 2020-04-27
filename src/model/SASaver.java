package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SASaver implements Saveable {

    public SASaver(){
    }

    public void save() throws IOException {
        HashMap<Date, Day> mapOfDays = ScheduleApp.getInstance().getMapOfDays();
        List<String> lines = new ArrayList<>();
        PrintWriter pw = new PrintWriter("savefile.txt", "UTF-8");

        for (Day d : mapOfDays.values()) {
            d.makeLines(lines);
        }
        for (String line : lines) {
            pw.println(line);
        }

        pw.close();
    }



}
