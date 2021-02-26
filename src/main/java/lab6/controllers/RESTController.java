package lab6.controllers;

import lab6.Main;
import lab6.dao.AddressBookRepo;
import lab6.model.AddressBook;
import lab6.model.BuddyInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("api")
public class RESTController {
    private static final Logger LOG = LoggerFactory.getLogger(RESTController.class);

    @Autowired
    private AddressBookRepo addrBookDAO;

    @PostMapping(path = "addrBook", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse createAddressBook() {
        LOG.info("Creating AddressBook");
        AddressBook addrBook = new AddressBook();
        addrBookDAO.save(addrBook);
        return new RestResponse("Created", addrBook.getId());
    }

    @DeleteMapping(path = "addrBook", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse removeAddressBook(@RequestParam(name = "addrBookId") Long id) {
        LOG.info("Removing AddressBook: " + id);
        if (addrBookDAO.existsById(id)) {
            addrBookDAO.deleteById(id);
        }
        return new RestResponse("Deleted", id);
    }

    @PutMapping(path = "buddy", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse addBuddy(@RequestParam(name="addrBookId") Long id, @RequestParam(name="name") String name, @RequestParam(name="address") String address) {
        LOG.info("Adding Buddy with name: " + name + " and Address: " + address + " to AddressBook: " + id);
        Optional<AddressBook> addrBook = addrBookDAO.findById(id);

        AtomicReference<RestResponse> restResponse = new AtomicReference<>();
        addrBook.ifPresentOrElse(
                addressBook -> {
                    addressBook.addBuddy(name, address);
                    addrBookDAO.save(addressBook);
                    restResponse.set(new RestResponse("Buddy Added", id));
                },
                () -> restResponse.set(new RestResponse("Address Book Not Found", id)));

        return restResponse.get();
    }

    @DeleteMapping(path = "buddy", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse removeBuddy(@RequestParam(name = "addrBookId") Long id, @RequestParam(name = "name") String name, @RequestParam(name = "address") String address) {
        LOG.info("Removing Buddy with name: " + name + " and Address: " + address + " from AddressBook: " + id);
        Optional<AddressBook> addrBook = addrBookDAO.findById(id);

        AtomicReference<RestResponse> restResponse = new AtomicReference<>();
        addrBook.ifPresentOrElse(
                addressBook -> {
                    Optional<BuddyInfo> badBuddy = addressBook.getBuddies().stream().filter(bud -> bud.getName().equals(name) && bud.getAddress().equals(address)).findFirst();
                    badBuddy.ifPresentOrElse(
                            bud -> {
                                addressBook.removeBuddy(bud);
                                addrBookDAO.save(addressBook);
                                restResponse.set(new RestResponse("Buddy Removed", id));
                            },
                            () -> restResponse.set(new RestResponse("Buddy Not Found", id)));
                },
                () -> restResponse.set(new RestResponse("Address Book Not Found", id)));

        return restResponse.get();
    }

    @ResponseBody
    public static class RestResponse {
        private final String message;
        private final Long addrBookId;

        private RestResponse(String message, Long addrBookId) {
            this.message = message;
            this.addrBookId = addrBookId;
        }

        public String getMessage() {
            return message;
        }

        public Long getAddrBookId() {
            return addrBookId;
        }
    }
}