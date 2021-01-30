package dataBase;

import dao.*;
import jpa.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Request {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("entityManager");
    private EntityManager entityManager = factory.createEntityManager();


    public ArrayList<RepairPost> getRpp() throws SQLException {
        RepairPostDao repairPosts = new RepairPostDao(entityManager);
        entityManager.getTransaction().begin();
        ArrayList<RepairPost> rpp = (ArrayList<RepairPost>) repairPosts.getAll();
        entityManager.getTransaction().commit();
        return rpp;
    }

    public ArrayList<Client> getAllClients() throws SQLException {
        ClientDao clientDao = new ClientDao(entityManager);
        entityManager.getTransaction().begin();
        ArrayList<Client> clients = (ArrayList<Client>) clientDao.getAll();
        entityManager.getTransaction().commit();
        return clients;
    }

    public ArrayList<Car> getAllCars() throws SQLException {
        CarDao carDao = new CarDao(entityManager);
        entityManager.getTransaction().begin();
        ArrayList<Car> cars = (ArrayList<Car>) carDao.getAll();
        entityManager.getTransaction().commit();
        return cars;
    }

    public Car getClientCar(Client client) throws SQLException {
        CarDao carDao = new CarDao(entityManager);
        entityManager.getTransaction().begin();
        Car car = (Car) carDao.getClientCar(client);
        entityManager.getTransaction().commit();
        return car;
    }

    public Client getOwner(Car car) throws SQLException {
        CarDao carDao = new CarDao(entityManager);
        entityManager.getTransaction().begin();
        Client client = (Client) carDao.getOwner(car);
        entityManager.getTransaction().commit();
        return client;
    }

    public ArrayList<RecordForRepairs> getRecordForRepairs(RepairPost post, LocalDate localDate) throws SQLException {
        RecordForRepairsDao record = new RecordForRepairsDao(entityManager);
        entityManager.getTransaction().begin();
        ArrayList<RecordForRepairs> recordForRepairs = (ArrayList<RecordForRepairs>) record.getRecord(post, localDate);
        entityManager.getTransaction().commit();
        return recordForRepairs;
    }

    public boolean recordingByNumber(String carNumber, Date dateStart, Date dateEnd, RepairPost repairPost, String desc) throws SQLException {
        CarDao carDao = new CarDao(entityManager);
        entityManager.getTransaction().begin();
        Car car = (Car) carDao.getByNumber(carNumber);
        //ClientDao clientDao = new ClientDao(entityManager);
        if (car == null) {
            System.out.println("Автомобиль не найден в базе!");
            entityManager.getTransaction().commit();
            return false;
        }
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = dateStart.toInstant();
        LocalDateTime dateStart1 = instant.atZone(defaultZoneId).toLocalDateTime();

        Instant instant2 = dateEnd.toInstant();
        LocalDateTime dateEnd1 = instant2.atZone(defaultZoneId).toLocalDateTime();


        RecordForRepairs recordForRepairs = new RecordForRepairs(car, car.getOwner(), repairPost, dateStart1, dateEnd1, desc);
        if (recordBusy(recordForRepairs, repairPost)){
            entityManager.getTransaction().commit();
            return false;
        }
        RecordForRepairsDao recordForRepairsDao = new RecordForRepairsDao(entityManager);
        recordForRepairsDao.add(recordForRepairs);
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean recordingByCar(Car car, Date dateStart, Date dateEnd, RepairPost repairPost, String desc) throws SQLException {
        entityManager.getTransaction().begin();
       // ClientDao clientDao = new ClientDao(entityManager);
        if (car == null) {
            //System.out.println("Автомобиль не найден в базе!");
            entityManager.getTransaction().commit();
            return false;
        }
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = dateStart.toInstant();
        LocalDateTime dateStart1 = instant.atZone(defaultZoneId).toLocalDateTime();

        Instant instant2 = dateEnd.toInstant();
        LocalDateTime dateEnd1 = instant2.atZone(defaultZoneId).toLocalDateTime();


        RecordForRepairs recordForRepairs = new RecordForRepairs(car, car.getOwner(), repairPost, dateStart1, dateEnd1, desc);
        if (recordBusy(recordForRepairs, repairPost)){
            entityManager.getTransaction().commit();
            return false;
        }
        RecordForRepairsDao recordForRepairsDao = new RecordForRepairsDao(entityManager);
        recordForRepairsDao.add(recordForRepairs);
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean recordBusy(RecordForRepairs recordForRepairs, RepairPost repairPost){
        RecordForRepairsDao recordForRepairsDao = new RecordForRepairsDao(entityManager);
        List<RecordForRepairs> listRec = recordForRepairsDao.getRecord(repairPost, recordForRepairs.getTimeStart(), recordForRepairs.getTimeStop());
        if (listRec.size() > 0){
            return true;
        }
        return false;
    }

    public boolean addClient(String name, String surname, Date birthdate, String tel) {
        ClientDao clientDao = new ClientDao(entityManager);
        entityManager.getTransaction().begin();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = birthdate.toInstant();
        LocalDate birthdate1 = instant.atZone(defaultZoneId).toLocalDate();
        ContactInfo contactInfo = null;
        contactInfo = addContact(tel);
        Client client = new Client(name, surname, birthdate1, contactInfo);
        try {
            clientDao.add(client);
        } catch (SQLException throwables) {
            entityManager.getTransaction().commit();
            throwables.printStackTrace();
            return false;
        }
        entityManager.getTransaction().commit();


        return true;
    }

    public boolean addCar(String model, String number,int year, Client owner) {
        CarDao carDao = new CarDao(entityManager);
        entityManager.getTransaction().begin();
        Car car = new Car(model, number, year, owner);
        try {
            carDao.add(car);
        } catch (SQLException throwables) {
            entityManager.getTransaction().commit();
            throwables.printStackTrace();
            return false;
        }
        entityManager.getTransaction().commit();
        return true;
    }


    public ContactInfo addContact(String contact)  {
        ContactInfoDao contactInfoDao = new ContactInfoDao(entityManager);
        if (entityManager.getTransaction().isActive()) {
            ContactInfo contactInfo = null;
            try {
                contactInfo = contactInfoDao.add(contact);
                return contactInfo;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }
       // entityManager.getTransaction().commit();
        return null;
    }

    public void deleteRec(RecordForRepairs recordForRepairs) throws SQLException {
        RecordForRepairsDao recordForRepairsDao = new RecordForRepairsDao(entityManager);
        entityManager.getTransaction().begin();
        recordForRepairsDao.removeByPK(recordForRepairs.getId());
        entityManager.getTransaction().commit();
    }

}
