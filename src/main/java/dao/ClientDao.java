package dao;

import jpa.entity.Client;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class ClientDao implements Dao<Client, Integer>{
    private EntityManager manager;

    public ClientDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Client entity) throws SQLException {
        if ((getByNameAndSurname(entity.getName(), entity.getSurname()) == null)) {
            manager.persist(entity);
        }
    }

    @Override
    public void update(Client entity) throws SQLException {
        manager.merge(entity);
    }

    @Override
    public void removeByPK(Integer id) throws SQLException {
        Client client = getByPK(id);
        if (client != null){
            manager.remove(client);
        }
    }

    @Override
    public Client getByPK(Integer id) throws SQLException {
        return manager.find(Client.class, id);
    }

    @Override
    public List<Client> getAll() throws SQLException {
        Query query = manager.createQuery("SELECT a FROM Client a");
        List<Client> clients = query.getResultList();
        return  clients;
    }

    public List<Client> getByNameAndSurname(String name, String surname) throws SQLException {
        Query query = manager.createQuery("SELECT c FROM Client c WHERE c.name = :name AND c.surname = :surname");
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        List<Client> clients = query.getResultList();
        if (clients.size() > 0) {
            return clients;
        }
        return null;
    }

}
