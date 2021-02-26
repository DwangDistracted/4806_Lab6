package lab6.controllers;

import lab6.dao.AddressBookRepo;
import lab6.model.AddressBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class GUIController {
    @Autowired
    private AddressBookRepo addrBookDAO;

    @GetMapping()
    public String viewAddressBook(@RequestParam(name = "addrBookId", defaultValue = "-1") Long id, Model model) {
        AtomicReference<String> templateName = new AtomicReference<>();

        if (id == -1L) {
            templateName.set("ERROR");
            model.addAttribute("error", "Add Query Param 'addrBookId' to see address book details");
        } else {
            Optional<AddressBook> addrBook = addrBookDAO.findById(id);
            addrBook.ifPresentOrElse(
                    (addressBook -> {
                        StringBuilder addrBookBuddies = new StringBuilder();
                        if (addressBook.getBuddies().size() == 0) {
                            addrBookBuddies.append("No Buddies in Address Book");
                        } else {
                            addressBook.getBuddies().forEach(bud ->
                                addrBookBuddies.append(bud.toString()).append("\n")
                            );
                        }

                        model.addAttribute("addrBookId", id);
                        model.addAttribute("buddies", addrBookBuddies.toString());
                        templateName.set("ViewAddressBook");
                    }),
                    () -> {
                        model.addAttribute("error", "Address Book with Id '" + id + "' not found");
                        templateName.set("ERROR");
                    }
            );
        }

        return templateName.get();
    }
}
