package lab5.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "AddressBook")
public class AddressBook implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany()
    @Cascade(CascadeType.ALL)
    private List<BuddyInfo> buddies;

    public AddressBook() {
        buddies = new ArrayList<>();
    }

    public AddressBook(AddressBook that) {
        this.id = that.id;
        this.buddies = new ArrayList<>(that.buddies);
    }

    public long getId() {
        return id;
    }

    public void addBuddy(String buddyName, String address) {
        addBuddy(new BuddyInfo(buddyName, address));
    }
    public void addBuddy(BuddyInfo buddy) {
        buddies.add(buddy);
    }

    public void removeBuddy(BuddyInfo buddy) {
        buddies.remove(buddy);
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof AddressBook)) {
            return false;
        } else if (that == this) {
            return true;
        } else {
            AddressBook thatAddrBook = (AddressBook) that;
            return this.buddies.size() == thatAddrBook.buddies.size() &&
                    thatAddrBook.buddies.containsAll(this.buddies) &&
                    this.buddies.containsAll(thatAddrBook.buddies);
        }
    }

    @Override
    public String toString() {
        StringBuilder contents = new StringBuilder();
        if (buddies.size() == 0) {
            contents.append("\tEMPTY");
        } else {
            buddies.forEach(bud -> contents.append("\t" + bud.toString() + ","));
            contents.replace(contents.length()-1, contents.length(), "");
        }

        return String.format("AddressBook[id=%d] with members:", id) + contents.toString();
    }
}
