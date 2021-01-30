package dao;

import jpa.entity.RepairPost;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class RepairPostDao implements Dao<RepairPost, Integer>{

    private EntityManager manager;

    public RepairPostDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(RepairPost entity) throws SQLException {
        if (getByName(entity.getName()) == null) {
            manager.persist(entity);
        }
    }

    @Override
    public void update(RepairPost entity) throws SQLException {
        manager.merge(entity);
    }

    @Override
    public void removeByPK(Integer id) throws SQLException {
        RepairPost repairPost = getByPK(id);
        if (repairPost != null){
            manager.remove(repairPost);
        }
    }

    @Override
    public RepairPost getByPK(Integer id) throws SQLException {
        return manager.find(RepairPost.class, id);
    }
    public RepairPost getByName(Integer id) throws SQLException {
        return manager.find(RepairPost.class, id);
    }

    @Override
    public List<RepairPost> getAll() throws SQLException {
        Query query = manager.createQuery("SELECT a FROM RepairPost a");
        List<RepairPost> repairPost = query.getResultList();
        return  repairPost;
    }

    public RepairPost getByName(String id) throws SQLException {
        return manager.find(RepairPost.class, id);
    }
}
