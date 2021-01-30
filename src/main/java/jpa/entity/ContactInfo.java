package jpa.entity;

import javax.persistence.*;

@Entity
public class ContactInfo {
    @Id
    private String contactInfo;
    @Enumerated
    private ContactType contactType;

    public ContactInfo() {
    }

    public ContactInfo(String contactInfo, ContactType contactType) {
        this.contactInfo = contactInfo;
        this.contactType = contactType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    @Override
    public String toString() {
        return contactType + ": " + contactInfo;
    }
}
