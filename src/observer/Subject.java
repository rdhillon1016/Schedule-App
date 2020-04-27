package observer;

import model.Day;
import model.Meal;
import model.MealMonitor;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<DayObserver> observers = new ArrayList<>();

    public void addObserver(DayObserver dayobserver){
        if (!observers.contains(dayobserver)){
            observers.add(dayobserver);
        }
    }

    public void notifyObservers(Day d, List<Meal> m){
        for (DayObserver dob : observers){
            dob.update(d, m);
        }
    }
}
