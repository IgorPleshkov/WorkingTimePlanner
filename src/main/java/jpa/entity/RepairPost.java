package jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
public class RepairPost{
    @Id
    private String name;
    @Enumerated
    private RepairType repairType;
    @Column(nullable = false)
    private LocalTime timeStart;
    @Column(nullable = false)
    private LocalTime timeStop;
    @Column
    public static final int RECORDING_INTERVAL = 30; //интервал записи в минутах
    @Column
    public static final LocalTime STARTING_JOB = LocalTime.of(9, 00);




    public RepairPost() {
    }

    public RepairPost(String name, RepairType repairType, LocalTime timeStart, LocalTime timeStop, int recordingInterval) {
        this.name = name;
        this.repairType = repairType;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
       // this.recordingInterval = recordingInterval;
    }

    public static final Date getTime(){
        Calendar time = new GregorianCalendar(0000, 00, 1, 9, 00);
        return time.getTime();
    }

    public String getName() {
        return name;
    }

  // public int getRecordingInterval() {
    //    return recordingInterval;
    //}

    @Override
    public String toString() {
        return name;
    }
}
