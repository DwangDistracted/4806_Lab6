package lab5.controllers;

import lab5.dao.AddressBookRepo;
import lab5.model.AddressBook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("view")
public class GUIController {
    @Autowired
    private AddressBookRepo addrBookDAO;

    @GetMapping()
    public String viewAddressBook(@RequestParam(name = "addrBookId") Long id, Model model) {
        Optional<AddressBook> addrBook = addrBookDAO.findById(id);

        AtomicReference<String> templateName = new AtomicReference<>();
        addrBook.ifPresentOrElse(
                (addressBook -> {
                    StringBuilder addrBookBuddies = new StringBuilder();
                    if (addressBook.getBuddies().size() == 0) {
                        addrBookBuddies.append("No Buddies in Address Book");
                    } else {
                        addressBook.getBuddies().forEach(bud -> {
                            addrBookBuddies.append(bud.toString() + "\n");
                        });
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

        return templateName.get();
    }
}
