package lab6.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuddyInfoTest {
    @Test
    public void setAddressChangesBuddyAddress() {
        BuddyInfo testBuddy = new BuddyInfo("Imaginary Buddy", "Imaginationland");
        assertEquals("Imaginationland", testBuddy.getAddress());
        testBuddy.setAddress("South Park");
        assertEquals("South Park", testBuddy.getAddress());
    }
}