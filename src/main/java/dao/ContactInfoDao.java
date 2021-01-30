package dao;

import jpa.entity.ContactInfo;
import jpa.entity.ContactType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class ContactInfoDao implements Dao<ContactInfo, Integer>{
    private EntityManager manager;

    public ContactInfoDao(EntityManager manager) {
        this.manager = manager;
    }
    @Override
    public void add(ContactInfo entity) throws SQLException {
        if (getByName(entity.getContactInfo()) == null) {
            manager.persist(entity);
        }
    }


    @Override
    public void update(ContactInfo entity) throws SQLException {
        manager.merge(entity);
    }

    @Override
    public void removeByPK(Integer id) throws SQLException {
        ContactInfo contactInfo = getByPK(id);
        if (contactInfo != null){
            manager.remove(contactInfo);
        }
    }

    @Override
    public ContactInfo getByPK(Integer id) throws SQLException {
        return manager.find(ContactInfo.class, id);
    }

    public ContactInfo getByName(String id) throws SQLException {
        return manager.find(ContactInfo.class, id);
    }

    @Override
    public List<ContactInfo> getAll() throws SQLException {
        Query query = manager.createQuery("SELECT a FROM ContactInfo a");
        List<ContactInfo> contactInfo = query.getResultList();
        return  contactInfo;
    }

    public ContactInfo add(String contact) throws SQLException {
        ContactInfo contactInfo = new ContactInfo();
        if (contact != null && !contact.trim().equals("")) {
            contactInfo = new ContactInfo();
            contactInfo.setContactInfo(contact);
            contactInfo.setContactType(ContactType.PHONE);
            manager.persist(contactInfo);
        }
        return contactInfo;
    }
}
