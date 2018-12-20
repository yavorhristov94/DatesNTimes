package net.twinray;

import java.time.*;
import java.util.Calendar;

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
}
