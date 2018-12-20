package net.twinray;

import java.time.*;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
	// create a calendar
        Clock testClock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
        LocalDate testDate = LocalDate.now(testClock);
        Calendar calendar = new Calendar();

    // add some tasks to the calendar
        calendar.addTask(1, 0, "Answer urgent e-mail");
        calendar.addTask(4, 0, "Write deployment report");
        calendar.addTask(4, 0, "Plan security configuration");

    // add some work periods to the calendar
        calendar.addWorkPeriod(Units.generateWorkPeriods(testDate, 3));

    // add an event to the calendar, specifying its time zone
        ZonedDateTime meetingTime = ZonedDateTime.of(testDate.atTime(8, 30),
                ZoneId.of("America/New_york"));

    // create a working schedule
        Schedule schedule = calendar.createSchedule(testClock);

    // and print it out
        System.out.println(schedule);
    }

    public static void Demo2(){
        Task t1 = new Task (30, "t1");
        Task t2 = new Task (60, "t2");
        Task t3 = new Task (30, "t3");

        LocalDate testDate = LocalDate.of(2018, 5, 22);
        LocalTime testTime = LocalTime.of(9, 0);
        LocalDateTime startDT = LocalDateTime.of(testDate,testTime);

        WorkPeriod wp1 = new WorkPeriod(startDT, Duration.ofHours(2));

        wp1.setTaskParts(Stream.of(t1,t2,t3)
                    .map(TaskPart::wholeOf)
                    .collect(toList()));

        System.out.println(wp1.toString());


        Optional<WorkPeriod> wp2 = wp1.split(startDT.plusHours(1));

        System.out.println(wp2.toString());
        System.out.println(wp1.toString());
    }
}
