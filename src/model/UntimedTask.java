package model;

import java.util.ArrayList;
import java.util.List;

public class UntimedTask extends GeneralTask {

    public UntimedTask(){
        super();
    }

    @Override
    public void setDay(Day d){
        this.day = d;
        d.addUntimedTask(this);
    }

    @Override
    public void makeLines(List<String> lines) {
        List<String> lineContents = new ArrayList<>();
        lineContents.add("untimed");
        lineContents.add(getNumber().toString());
        lineContents.add(getTaskDescription());
        lineContents.add(day.dayToFormatString());
        lines.add(constructLine(lineContents));
    }

    @Override
    public void displayCombo(){
        displayTaskNumber();
        displayTaskDescription();
    }

}
