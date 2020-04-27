package model;

import exceptions.InvalidCaloriesException;
import exceptions.InvalidDayException;

import java.io.IOException;
import java.text.ParseException;

public interface Loadable {

    void load() throws IOException, InvalidCaloriesException, InvalidDayException, ParseException;
}
