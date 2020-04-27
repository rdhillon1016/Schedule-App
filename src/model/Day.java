package model;

import exceptions.TaskOverlapException;

import java.text.SimpleDateFormat;
import java.util.*;

public class Day extends SavingLineMaker implements Comparable<Day> {
    private Date date;
    private List<Task> tasks;
    private List<UntimedTask> uttasks;
    private List<Meal> meals;
    private Scanner sc;
    private SimpleDateFormat sdf;

    public Day(Date d) {
        date = d;
        tasks = new ArrayList<>();
        uttasks = new ArrayList<>();
        meals = new ArrayList<>();
        sc = new Scanner(System.in);
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        addObserver(new MealMonitor());
    }

    @Override
    public int compareTo(Day d) {
        return date.compareTo(d.getDate());
    }

    // REQUIRES task n
    // EFFECTS checks if n overlaps with anything in mapOfTasks
    public void checkTaskDatesOverlap(Task n) throws TaskOverlapException {
        for (Task t : tasks) {
            if (n.checkTaskDateOverlap(t)) {
                throw new TaskOverlapException();
            }
        }
    }

    private void orderRegTasksByStartDate() {
        Collections.sort(tasks);
    }

    // ALL ASSIGN NUMBERS METHODS
    public void assignTaskNumbers() {
        int i = 1;
        for (Task t : tasks) {
            t.setNumber(i);
            i++;
        }
    }

    public void assignUntimedTaskNumbers() {
        int i = tasks.size() + 1;
        for (UntimedTask ut : uttasks) {
            ut.setNumber(i);
            i++;
        }
    }

    public void assignMealNumbers() {
        int i = tasks.size() + uttasks.size() + 1;
        for (Meal m : meals) {
            m.setNumber(i);
            i++;
        }
    }

    // ALL DISPLAY METHODS
    public void displayAllTasks() {
        System.out.println(" ");
        displayDate();
        System.out.println(" ");
        orderRegTasksByStartDate();
        assignTaskNumbers();
        assignUntimedTaskNumbers();
        assignMealNumbers();
        System.out.println("Timed Tasks");
        displayTimedTasks();
        System.out.println(" ");
        System.out.println("Untimed Tasks");
        displayUntimedTasks();
        System.out.println(" ");
        System.out.println("Meals");
        System.out.println(" ");
        displayMeals();

    }

    private void displayTimedTasks() {
        for (Task t : tasks) {
            t.displayCombo();
            System.out.println(" ");
        }
    }

    private void displayUntimedTasks() {
        for (UntimedTask ut : uttasks) {
            ut.displayCombo();
            System.out.println(" ");
        }
    }

    private void displayMeals() {
        for (Meal m : meals) {
            m.displayCombo();
            System.out.println(" ");
        }
    }

    public String getDateAsString(){
        return sdf.format(date);
    }

    private void displayDate() {
        System.out.println(sdf.format(date));
    }

    // REMOVE AND CHANGE METHODS
    // REQUIRES a positive integer that is <= the size of list, non empty list
    // MODIFIES this
    // EFFECTS removes a task
    public void removeATask(int num) {
        if (tasks.size() + uttasks.size() + meals.size() > 0) {
            displayAllTasks();
            if (num > 0 && num <= tasks.size() + uttasks.size() + meals.size()) {
                if (num <= tasks.size()) {
                    tasks.remove(num - 1);
                } else if (num <= uttasks.size() + tasks.size()) {
                    uttasks.remove(num - tasks.size() - 1);
                } else {
                    meals.remove(num - tasks.size() - uttasks.size() - 1);
                }
            }
            assignAllNumbers();
        } else {
            System.out.println("You have nothing to remove!");
        }
    }

    public void assignAllNumbers(){
        assignTaskNumbers();
        assignUntimedTaskNumbers();
        assignMealNumbers();
    }

    // ALL ADD METHODS
    public void addMeal(Meal n) {
        if (!meals.contains(n)) {
            meals.add(n);
            n.setDay(this);
            notifyObservers(this, meals);
        }
    }

    public void addUntimedTask(UntimedTask ut) {
        if (!uttasks.contains(ut)) {
            uttasks.add(ut);
            ut.setDay(this);
        }
    }

    public void addTask(Task t) {
        if (!tasks.contains(t)) {
            tasks.add(t);
            t.setDay(this);
        }
    }

    // GETTERS
    public Date getDate() {
        return date;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<UntimedTask> getUttasks() {
        return uttasks;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    //LOAD/SAVE
    public void makeLines(List<String> lines) {
        List<String> lineContents = new ArrayList<>();
        lineContents.add("day");
        lineContents.add(sdf.format(date));
        lines.add(constructLine(lineContents));
        if (!(tasks.size() == 0)) {
            for (Task t : tasks) {
                t.makeLines(lines);
            }
        }
        if (!(uttasks.size() == 0)) {
            for (UntimedTask ut : uttasks) {
                ut.makeLines(lines);
            }
        }
        if (!(meals.size() == 0)) {
            for (Meal m : meals) {
                m.makeLines(lines);
            }
        }
    }

    public String dayToFormatString() {
        return sdf.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Objects.equals(date, day.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date);
    }
}
