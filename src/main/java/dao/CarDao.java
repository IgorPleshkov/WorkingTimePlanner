package dao;
import jpa.entity.Car;
import jpa.entity.Client;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class CarDao implements Dao<Car, Integer>{
    private EntityManager manager;

    public CarDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Car entity) throws SQLException {
        if (getByNumber(entity.getStateNumber()) == null) {
            manager.persist(entity);
        }
    }

    @Override
    public void update(Car entity) throws SQLException {
        manager.merge(entity);
    }


    @Override
    public void removeByPK(Integer id) throws SQLException {
        Car car = getByPK(id);
        if (car != null){
            manager.remove(car);
        }
    }

    @Override
    public Car getByPK(Integer id) throws SQLException {
        return manager.find(Car.class, id);
    }

    public Car getByNumber(String id) throws SQLException {
        return manager.find(Car.class, id);
    }


    @Override
    public List<Car> getAll() throws SQLException {
        Query query = manager.createQuery("SELECT a FROM Car a");
        List<Car> cars = query.getResultList();
        return  cars;
    }

    public Car getClientCar(Client client) throws SQLException {
        Query query = manager.createQuery("SELECT c FROM Car c WHERE c.owner = :client");
        query.setParameter("client", client);
        List<Car> cars = query.getResultList();
        if (cars.size() > 0) {
            return cars.get(0);
        }
        return null;
    }

    public Client getOwner(Car car) throws SQLException {
        Query query = manager.createQuery("SELECT c.owner FROM Car c WHERE c = :car");
        query.setParameter("car", car);
        List<Client> clients = query.getResultList();
        if (clients.size() > 0) {
            return clients.get(0);
        }
        return null;
    }


}
