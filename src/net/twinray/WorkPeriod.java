package net.twinray;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class WorkPeriod {
    LocalDateTime startTime;
    LocalDateTime endtime;

    public WorkPeriod(LocalDateTime startTime, LocalDateTime endtime) {
        this.startTime = startTime;
        this.endtime = endtime;
    }
}
