package lab5.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;

@Entity(name = "BuddyInfo")
public class BuddyInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String buddyName;
    private String address;

    public BuddyInfo() {
        buddyName = "";
        address = "";
    }
    public BuddyInfo(String buddyName, String address) {
        this.buddyName = buddyName;
        this.address = address;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return buddyName;
    }
    public void setName(String buddyName) {
        this.buddyName = buddyName;
    }

    public void setAddress(String newAddress) {
        this.address = newAddress;
    }
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return String.format("BuddyInfo[id=%d, name='%s', address='%s']", id, buddyName, address);
    }

    @Override
    public boolean equals(Object that) {
        if(!(that instanceof BuddyInfo)) {
            return false;
        } else if (that == this) {
            return true;
        } else {
            BuddyInfo thatBuddy = (BuddyInfo) that;
            return this.buddyName.equals(thatBuddy.buddyName) &&
                    this.address.equals(thatBuddy.address);
        }
    }
}
