package hello.controller;

import com.google.gson.Gson;
import hello.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import hello.service.ContactService;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class WebController {

    @Autowired
    private ContactService contactService;

    @RequestMapping("/save")
    public String process() {
        contactService.save(new Contact("Contact1"));
        contactService.save(new Contact("Contact2"));
        contactService.save(new Contact("Contact3"));
        contactService.save(new Contact("Contact3"));
        contactService.save(new Contact("Contact3"));
        contactService.save(new Contact("Contact3"));
        contactService.save(new Contact("Contact3"));
        contactService.save(new Contact("Contact3"));
        return "Done";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public @ResponseBody String findAll(@RequestParam(value = "nameFilter") String regex) {
        List<Contact> contacts = new LinkedList<Contact>();
        for (Contact contact : contactService.findAllContacts()) {
            if (contact.getName().matches(regex)) {
                contacts.add(contact);
            }
        }
        return "contacts: " + new Gson().toJson(contacts);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public @ResponseBody String viewCustomers(@RequestParam(value = "number") int pageNumber) {
        List<Contact> contacts = contactService.getPage(pageNumber);
        return "contacts: " + new Gson().toJson(contacts);
    }
}