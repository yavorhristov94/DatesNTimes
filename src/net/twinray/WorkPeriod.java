package net.twinray;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class WorkPeriod {
    LocalDateTime startTime;
    LocalDateTime endTime;
    Duration duration;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public WorkPeriod(LocalDateTime startTime, LocalDateTime endtime) {
        this.startTime = startTime;
        this.endTime = endtime;
    }

    public WorkPeriod(LocalDateTime startTime, Duration duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

    //Split at your chosen time
    public Optional<WorkPeriod> split(LocalDateTime splitTime) {
        if (startTime.isBefore(splitTime) && splitTime.isBefore((endTime))){
            WorkPeriod newPeriod =
                    new WorkPeriod(startTime, Duration.between(startTime, splitTime));
            startTime = splitTime;
            if(!taskParts.isEmpty()) { //if there are tasks in the period
                NavigableMap<LocalDateTime, TaskPart> timeToTaskPartMap = new TreeMap<>();
                LocalDateTime taskStartTime = newPeriod.getStartTime(); //find where the period starts
                for (TaskPart taskPart : taskParts){
                    timeToTaskPartMap.put(taskStartTime, taskPart); //and map the task parts onto that period start
                    taskStartTime = taskStartTime.plus(taskPart.getDuration);
                }
                //The map above is used to put the tasks into the periods - based on if they start before or after the
                //split time

                //Tasks that are after the split time are put into the new period
                newPeriod.setTaskParts(new ArrayList<>(timeToTaskPartMap.headMap(splitTime).values()));

                //tasks before it, into the old one
                setTaskParts(new ArrayList<>(timeToTaskPartMap.tailMap(splitTime).values()));


                //Finally, if the task lands on the split time (which it likely will), it gets split...
                Map.Entry<LocalDateTime, TaskPart> taskPartEntry = timeToTaskPartMap.lowerEntry(splitTime);
                TaskPart partToSpplit = taskPartEntry.getValue();
                LocalDateTime partStartTime = taskPartEntry.getKey();
                Duration partDuration = partToSpplit.getDuration();

                if(splitTime.isAfter(partStartTime) && partStartTime.plus(partDuration).isBefore(splitTime)){
                    TaskPart newTaskPart = partToSpplit.split(Duration.between(partStartTime, splitTime));
                    //Method takes the duration for the first part, somehow figures out the remaining duration and
                    //gives it to the new task part
                    getTaskParts().add(0, newTaskPart);
                    //and this finally puts the starter task at the start of the period
                    //NEVERMIND the fact that this may cause the period to overflow since this isnt done FIRST!
                }

            }return Optional.of(newPeriod);
        }
        else return Optional.empty();
    }


    //Split at midnight, only works on midnight spanning workperiods
    public Optional<WorkPeriod> split() {
        LocalDateTime midnight = startTime.toLocalDate().plusDays(1).atStartOfDay();
        return split(midnight);
        //by just setting the start at the next day, start of day
    }
}



