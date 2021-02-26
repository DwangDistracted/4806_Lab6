package lab6.controllers;

import lab6.dao.AddressBookRepo;
import lab6.model.AddressBook;
import lab6.model.BuddyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class GUIController {
    private static final Logger LOG = LoggerFactory.getLogger(GUIController.class);

    @Autowired
    private AddressBookRepo addrBookDAO;

    @GetMapping("part1")
    public String getPartOne() {
        return "Select";
    }

    @GetMapping("part2")
    public String getPartTwo() {
        return "Part2";
    }

    @GetMapping()
    public String viewAddressBook(@RequestParam(name = "addrBookId", defaultValue = "-1") Long id, Model model) {
        AtomicReference<String> templateName = new AtomicReference<>();

        if (id == -1L) {
            templateName.set("Select");
        } else {
            Optional<AddressBook> addrBook = addrBookDAO.findById(id);
            addrBook.ifPresentOrElse(
                    (addressBook -> {
                        model.addAttribute("addrBookId", id);
                        model.addAttribute("buddies", renderAddressBookBuddies(addressBook));
                        templateName.set("ViewAddressBook");
                    }),
                    () -> templateName.set("Select")
            );
        }

        return templateName.get();
    }

    @GetMapping(path = "create")
    public String createAddressBookForPart1(Model model) {
        LOG.info("Creating AddressBook");
        AddressBook addrBook = new AddressBook();
        addrBookDAO.save(addrBook);

        model.addAttribute("addrBookId", addrBook.getId());
        model.addAttribute("buddies", renderAddressBookBuddies(addrBook));
        return "ViewAddressBook";
    }

    @GetMapping(path = "addBuddy")
    public String addBuddyForPart1(@RequestParam(name = "addrBookId", defaultValue = "-1") Long addrBookId,
                                   @RequestParam(name = "buddyName") String buddyName,
                                   @RequestParam(name = "address") String buddyAddress, Model model) {
        LOG.info("Adding Buddy to AddressBook:" + addrBookId);
        Optional<AddressBook> addressBook = addrBookDAO.findById(addrBookId);
        if (addressBook.isPresent()) {
            AddressBook addrBook = addressBook.get();
            addrBook.addBuddy(buddyName, buddyAddress);
            addrBookDAO.save(addrBook);

            model.addAttribute("addrBookId", addrBook.getId());
            model.addAttribute("buddies", renderAddressBookBuddies(addrBook));
            return "ViewAddressBook";
        } else {
            return "Select";
        }
    }

    @GetMapping(path = "removeBuddy")
    public String removeBuddyForPart1(@RequestParam(name = "addrBookId", defaultValue = "-1") Long addrBookId,
                                      @RequestParam(name = "buddyId", defaultValue = "-1") Long buddyId, Model model) {
        LOG.info("Removing Buddy from AddressBook:" + addrBookId);
        Optional<AddressBook> addressBook = addrBookDAO.findById(addrBookId);
        if (addressBook.isPresent()) {
            AddressBook addrBook = addressBook.get();
            addrBook.removeBuddy(buddyId);
            addrBookDAO.save(addrBook);

            model.addAttribute("addrBookId", addrBook.getId());
            model.addAttribute("buddies", renderAddressBookBuddies(addrBook));
            return "ViewAddressBook";
        } else {
            return "Select";
        }
    }

    @GetMapping(path = "delete")
    public String deleteAddressBookForPart1(@RequestParam(name = "addrBookId", defaultValue = "-1") Long addrBookId, Model model) {
        LOG.info("Deleting AddressBook");
        if (addrBookId == -1 ) {
            return "Select";
        } else {
            addrBookDAO.deleteById(addrBookId);
            return "Select";
        }
    }

    private String renderAddressBookBuddies(AddressBook addressBook) {
        StringBuilder addrBookBuddies = new StringBuilder();
        if (addressBook.getBuddies().size() == 0) {
            addrBookBuddies.append("No Buddies in Address Book");
        } else {
            addressBook.getBuddies().forEach(bud ->
                    addrBookBuddies.append(bud.toString()).append("\n")
            );
        }
        return addrBookBuddies.toString();
    }
}
