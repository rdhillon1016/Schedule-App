package model;

import observer.DayObserver;

import java.util.List;

public class MealMonitor implements DayObserver {

    public void update(Day d, List<Meal> m) {
        System.out.println("You have added a new meal to: " + d.getDateAsString());
        int i = 0;
        for (Meal n : m){
            i += n.getCalories();
        }
        System.out.println("Your total calories for " + d.getDateAsString() + " is " + Integer.toString(i));
    }
}
