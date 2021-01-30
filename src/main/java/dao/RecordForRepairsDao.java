package dao;

import jpa.entity.RecordForRepairs;
import jpa.entity.RepairPost;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class RecordForRepairsDao implements Dao<RecordForRepairs, Integer>{

    private EntityManager manager;

    public RecordForRepairsDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(RecordForRepairs entity) throws SQLException {
        manager.persist(entity);
    }

    @Override
    public void update(RecordForRepairs entity) throws SQLException {
        manager.merge(entity);
    }

    @Override
    public void removeByPK(Integer id) throws SQLException {
        RecordForRepairs recordForRepairs = getByPK(id);
        if (recordForRepairs != null){
            manager.remove(recordForRepairs);
        }
    }

    @Override
    public RecordForRepairs getByPK(Integer id) throws SQLException {
        return manager.find(RecordForRepairs.class, id);
    }

    @Override
    public List<RecordForRepairs> getAll() throws SQLException {
        Query query = manager.createQuery("SELECT a FROM RecordForRepairs a");
        List<RecordForRepairs> recordForRepairs = query.getResultList();
        return  recordForRepairs;
    }


    public List<RecordForRepairs> getRecord(RepairPost post, LocalDate dateTime){
        Query query = manager.createQuery("SELECT r FROM RecordForRepairs r WHERE r.repairPost = :repairPost AND r.timeStart BETWEEN :date1 AND :date2" );
        query.setParameter("repairPost", post);
        query.setParameter("date1", LocalDateTime.of(dateTime, LocalTime.MIN));
        query.setParameter("date2", LocalDateTime.of(dateTime, LocalTime.MAX));
        List<RecordForRepairs> recordForRepairs = query.getResultList();
        return recordForRepairs;
    }

    public List<RecordForRepairs> getRecord(RepairPost post, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd){
        Query query = manager.createQuery("SELECT r FROM RecordForRepairs r WHERE r.repairPost = :repairPost AND ((:dateTimeStart BETWEEN r.timeStart AND r.timeStop" +
                " OR :dateTimeEnd BETWEEN r.timeStart AND r.timeStop) OR (r.timeStart BETWEEN :dateTimeStart AND :dateTimeEnd))" );
        query.setParameter("repairPost", post);
        query.setParameter("dateTimeStart", dateTimeStart.plusMinutes(1));
        query.setParameter("dateTimeEnd", dateTimeEnd.minusMinutes(1));
        List<RecordForRepairs> recordForRepairs = query.getResultList();
        return recordForRepairs;
    }

}
