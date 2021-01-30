package jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Car{
    @Column(nullable = false)
    private String model;
    @Id
    private String stateNumber;
    @Column(nullable = false)
    private int year;
    @OneToOne
    private Client owner;

    public Car() {
    }

    public Car(String model, String stateNumber, int year, Client owner) {
        this.model = model;
        this.stateNumber = stateNumber;
        this.year = year;
        this.owner = owner;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public Client getOwner() {
        return owner;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return  model + " Гос. номер: " + stateNumber + " Год выпуска: " + year;
    }
}
