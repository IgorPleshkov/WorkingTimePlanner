package jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class RecordForRepairs extends BaseId{
    @OneToOne
    private Car car;
    @OneToOne
    private Client client;
    @OneToOne
    private RepairPost repairPost;
    @Column(nullable = false)
    private LocalDateTime timeStart;
    @Column(nullable = false)
    private LocalDateTime timeStop;
    @Column(nullable = false)
    private String description;

    public RecordForRepairs() {
    }

    public RecordForRepairs(Car car, Client client, RepairPost repairPost, LocalDateTime timeStart, LocalDateTime timeStop, String description) {
        this.car = car;
        this.client = client;
        this.repairPost = repairPost;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
        this.description = description;
    }

    @Override
    public String toString() {
        return car.getStateNumber();
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public LocalDateTime getTimeStop() {
        return timeStop;
    }

    public Car getCar() {
        return car;
    }

    public Client getClient() {
        return client;
    }

    public RepairPost getRepairPost() {
        return repairPost;
    }

    public String getDescription() {
        return description;
    }
}
