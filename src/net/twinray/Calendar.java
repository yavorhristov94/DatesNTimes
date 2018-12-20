package net.twinray;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calendar {
    LocalTime amStart = LocalTime.of(9, 0);
    LocalTime pmStart = LocalTime.of(13, 30);
    Duration workPeriodLength = Duration.ofHours(3).plusMinutes(30);


    public List<WorkPeriod> generateWorkPeriods (LocalDate date, int dayCount){
        //this method delegates its work to two other methods
        //Generate the working days
        List<LocalDate> workingDays = generateWorkingDays(date, dayCount);

        //and then generate the working periods in those days
        return generateWorkPeriods(workingDays, amStart, workPeriodLength,
                pmStart, workPeriodLength);
    }

    private List<LocalDate> generateWorkingDays(LocalDate startDate, int dayCount) {
        return Stream.iterate(startDate, d -> d.plusDays(1))
                .filter(Utils::isWorkingDay) //Removes non-working days
                .limit(dayCount) //stops iterating after the given amount of days
                .collect(Collectors.toList()); //turns it into a list
        //as a list is our return type
    }

    private List<WorkPeriod> generateWorkPeriods (List<LocalDate> workingDays,
      LocalTime amStart, Duration amDuration,
      LocalTime pmStart, Duration pmDuration){

     List<WorkPeriod> periods = new ArrayList<>();
     for (LocalDate d : workingDays){
         LocalDateTime thisAmStart = LocalDateTime.of(d, amStart); //starts a work period
         periods.add(new WorkPeriod(thisAmStart, thisAmStart.plus(amDuration)));
         //finishes the work period and adds it in

     }

    }
}
