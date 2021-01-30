package dataBase;

import dao.*;
import jpa.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddToBase {
    public static void addToBase() throws SQLException {

        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("entityManager");
        EntityManager entityManager = managerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        ContactInfoDao contactInfoDao = new ContactInfoDao(entityManager);
        ClientDao clientDao = new ClientDao(entityManager);
        CarDao carDao = new CarDao(entityManager);
        RepairPostDao repairPostDao = new RepairPostDao(entityManager);
        RecordForRepairsDao recordForRepairsDao = new RecordForRepairsDao(entityManager);

       ContactInfo contactInfo1 = new ContactInfo("+79214123677", ContactType.PHONE);
       ContactInfo contactInfo2 = new ContactInfo("+79110106255", ContactType.PHONE);



        Client client1 = new Client("Иван", "Иванов", LocalDate.now(),contactInfo1);
        Client client2 = new Client("Петр", "Петров", LocalDate.now(),contactInfo2);

        Car car1 = new Car("Volvo", "P142BM178", 2015, client1);
        Car car2 = new Car("Audi", "P221PM178", 2012, client2);

        RepairPost repairPost1 = new RepairPost("Мойка", RepairType.LOCKMITH, LocalTime.now(), LocalTime.now(),30);
        RepairPost repairPost2 = new RepairPost("Диагностика", RepairType.LOCKMITH, LocalTime.now(), LocalTime.now(),30);
        RepairPost repairPost3 = new RepairPost("Двигатель", RepairType.LOCKMITH, LocalTime.now(), LocalTime.now(),30);
        RepairPost repairPost4 = new RepairPost("Ходовая", RepairType.LOCKMITH, LocalTime.now(), LocalTime.now(),30);
        RepairPost repairPost5 = new RepairPost("Электрика", RepairType.LOCKMITH, LocalTime.now(), LocalTime.now(),30);
        RepairPost repairPost6 = new RepairPost("Кузовной участок", RepairType.LOCKMITH, LocalTime.now(), LocalTime.now(),30);

        RecordForRepairs recordForRepairs1 = new RecordForRepairs(car1, client1, repairPost3, LocalDateTime.now().minusHours(3), LocalDateTime.now().plusMinutes(30).minusHours(3), "Замена масла");
        RecordForRepairs recordForRepairs2 = new RecordForRepairs(car2, client2, repairPost5, LocalDateTime.now().minusHours(3), LocalDateTime.now().plusMinutes(90).minusHours(3), "Ремонт проводки");


        contactInfoDao.add(contactInfo1);
        clientDao.add(client1);
        contactInfoDao.add(contactInfo2);
        clientDao.add(client2);

        carDao.add(car1);
        carDao.add(car2);

        repairPostDao.add(repairPost1);
        repairPostDao.add(repairPost2);
        repairPostDao.add(repairPost3);
        repairPostDao.add(repairPost4);
        repairPostDao.add(repairPost5);
        repairPostDao.add(repairPost6);

        recordForRepairsDao.add(recordForRepairs1);
        recordForRepairsDao.add(recordForRepairs2);

        entityManager.getTransaction().commit();

        System.out.println("ПОСТЫ РЕМОНТА: " + repairPostDao.getAll());

    }
}
