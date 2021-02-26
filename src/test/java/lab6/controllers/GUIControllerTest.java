package lab6.controllers;

import lab6.dao.AddressBookRepo;
import lab6.model.AddressBook;
import lab6.model.BuddyInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= GUIController.class)
@AutoConfigureMockMvc
public class GUIControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressBookRepo addressBookRepo;

    @Test
    public void whenInvalidAddressBookIdGetErrorMsg() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").param("addrBookId", "0").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "Address Book with Id '0' not found"));
    }

    @Test
    public void whenValidAddressBookIdGetAddressIdAndBuddyList() throws Exception {
        AddressBook addrBook1 = new AddressBook();
        BuddyInfo imaginaryFren = new BuddyInfo("UNIT", "TEST");
        addrBook1.addBuddy(imaginaryFren);
        given(addressBookRepo.findById(any())).willReturn(java.util.Optional.of(addrBook1));

        mvc.perform(MockMvcRequestBuilders.get("/").param("addrBookId", "1").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attribute("addrBookId", 1L))
                .andExpect(model().attribute("buddies", imaginaryFren.toString() + "\n"));
    }

    @Test
    public void whenValidEmptyAddressBookIdGetAddressIdAndEmptyMsg() throws Exception {
        AddressBook addrBook1 = new AddressBook();
        given(addressBookRepo.findById(any())).willReturn(java.util.Optional.of(addrBook1));

        mvc.perform(MockMvcRequestBuilders.get("/").param("addrBookId", "1").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attribute("addrBookId", 1L))
                .andExpect(model().attribute("buddies", "No Buddies in Address Book"));
    }
}
