package observer;

import model.Day;
import model.Meal;

import java.util.List;

public interface DayObserver {
    void update(Day d, List<Meal> m);
}
