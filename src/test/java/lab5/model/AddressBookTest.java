package lab5.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressBookTest {
    @Test
    public void addBuddyWhenGivenBuddyInfoObject() {
        AddressBook addrBook = new AddressBook();
        BuddyInfo testBuddy = new BuddyInfo("testBuddy", "JUnit");

        addrBook.addBuddy(testBuddy);

        assertTrue(addrBook.getBuddies().contains(testBuddy));
    }

    @Test
    public void addBuddyWhenGivenNameAndAddress() {
        AddressBook addrBook = new AddressBook();

        addrBook.addBuddy("TestBuddyName", "TestBuddyAddress");

        assertTrue(addrBook.getBuddies().stream().anyMatch(
                bud -> "TestBuddyName".equals(bud.getName()) &&
                        "TestBuddyAddress".equals(bud.getAddress())));
    }

    @Test
    public void removeBuddy() {
        AddressBook addrBook = new AddressBook();
        BuddyInfo testBuddy = new BuddyInfo("testBuddy", "JUnit");

        addrBook.addBuddy(testBuddy);
        assertTrue(addrBook.getBuddies().contains(testBuddy));

        addrBook.removeBuddy(testBuddy);

        assertFalse(addrBook.getBuddies().contains(testBuddy));
    }

    @Test
    public void testCopyConstructor() {
        AddressBook addrBook1 = new AddressBook();
        BuddyInfo testBuddy = new BuddyInfo("testBuddy", "JUnit");

        addrBook1.addBuddy(testBuddy);
        AddressBook addrBook2 = new AddressBook(addrBook1);

        assertTrue(addrBook2.getBuddies().contains(testBuddy));
    }
}