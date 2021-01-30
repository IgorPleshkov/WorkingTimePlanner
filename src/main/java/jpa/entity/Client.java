package jpa.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Client extends BaseId{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private LocalDate birthdate;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private ContactInfo contactInfo;


    public Client() {
    }

    public Client(String name, String surname, LocalDate birthdate, ContactInfo contactInfo) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
