package net.twinray;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Utils {

    public static boolean isWorkingDay(LocalDate d){
        return d.getDayOfWeek() != DayOfWeek.SATURDAY &&
                d.getDayOfWeek() != DayOfWeek.SATURDAY.SUNDAY;
    }
}
