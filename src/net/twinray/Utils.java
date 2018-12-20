package net.twinray;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class Utils {

    public static boolean isWorkingDay(LocalDate d){
        return d.getDayOfWeek() != DayOfWeek.SATURDAY &&
                d.getDayOfWeek() != DayOfWeek.SATURDAY.SUNDAY;
    }

}
