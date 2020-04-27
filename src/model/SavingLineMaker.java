package model;

import observer.Subject;

import java.util.List;

public class SavingLineMaker extends Subject {

    public String constructLine(List<String> line){
        String s = "";
        for(String l: line){
            s = s + l + " ";
        }
        return s;
    }
}
