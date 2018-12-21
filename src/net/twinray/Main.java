package net.twinray;

import java.time.*;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
	// create a calendar
        Clock testClock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
        LocalDate testDate = LocalDate.now(testClock);
        net.twinray.Calendar calendar = new net.twinray.Calendar();

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

    public static void TimeZoneFlying(){
        //No idea where this method goes, it's just demo'd out of nowhere
        net.twinray.Calendar calendar = new net.twinray.Calendar();
        //also it's moronic to name your Classes like ones that exist
        List<WorkPeriod> wps = calendar.generateWorkPeriods(LocalDate.now(), 1);
        //We set up the list of work periods, as normal

        ZoneOffset origZone = ZoneOffset.of("+0");
        ZoneOffset destZone = ZoneOffset.of("-5");
        //hardcoding he zones in this demo
        LocalDateTime destLocalLandingTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11,0));
        //Hardcoding the destination time


        //The end goal is to see what activities will be available in time when we reach the dest
        OffsetDateTime destOffsetLandingTIme = OffsetDateTime.of(destLocalLandingTime, destZone);
        //We then use that to create an OffsetDateTime, which we can use for translating the local time to ours ATM
        OffsetDateTime origOffsetLandingTime = destOffsetLandingTIme.withOffsetSameInstant(origZone);
        //We tell the system to translate that destination's time to our time by putting in our time zone
        LocalDateTime origLocalLandingTime = origOffsetLandingTime.toLocalDateTime();
        //Finally, we can convert it to our local time, finding when we will have landed.

        //BTW this is all unnecessary bullshit, since you can just put in your flight time and then
        //manually adjust the time zone change, but fuck me, why should things make sense

        //We remove the ones that have ended already, are during landing (must be before and after landing)
        List<WorkPeriod> usableWorkPeriods = wps.stream()
                .filter(wp -> wp.getEndTime().isAfter(LocalDateTime.now()))
                .filter(wp -? wp.getEndTime().isAfter(origLocalLandingTime)) ||
                 wp.getStartTime().isAfter(origLocalLandingTime)
                .collect(toList());
     }
}
