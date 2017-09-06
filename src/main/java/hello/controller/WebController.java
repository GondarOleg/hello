package hello.controller;

import com.google.gson.Gson;
import hello.model.Contact;
import hello.model.ErrorJson;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import hello.service.ContactService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class WebController {

    @Autowired
    private ContactService contactService;

    private List<Contact> contacts = new LinkedList<Contact>();
    @Value("${page_size}")
    private int page_size;


    @RequestMapping("/save")
    public HttpStatus save() {
        contactService.save(new Contact("Contact1"));
        contactService.save(new Contact("Contact2"));
        contactService.save(new Contact("Contact3"));
        contactService.save(new Contact("Contact4"));
        contactService.save(new Contact("Contact5"));
        contactService.save(new Contact("Contact6"));
        contactService.save(new Contact("Contact7"));
        contactService.save(new Contact("^.*[aei].*$"));
        return HttpStatus.OK;
    }

    @RequestMapping("/delete_all")
    public HttpStatus delete() {
        contactService.deleteAll();
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public @ResponseBody
    String findByKey(@RequestParam(value = "nameFilter") String regex, @RequestParam(value = "page", required = false) Integer page_num) throws Exception {
        int page_number = 1;
        int pages_total = 0;
        int total_record_count = 0;
        contacts = searchContacts(regex);
        total_record_count = contacts.size();
        if (page_num != null) {
            page_number = page_num;
        }
        pages_total = total_record_count/page_size;
        if (page_number>pages_total){
            throw new NotFoundException("Page not fount", new NotFoundException("Page index out of range!!!"));
        }
        return makePaginatedHeader(page_number, total_record_count) + makeJson(page_number, total_record_count);
    }

    @RequestMapping("/findall")
    public @ResponseBody
    Iterable<Contact> findAll(){
        return contactService.findAllContacts();
    }

    private List<Contact> searchContacts(String regexForSearch) {
        List<Contact> contacts = new LinkedList<Contact>();
        for (Contact contact : contactService.findAllContacts()) {
            if (contact.getName().matches(regexForSearch) && !contact.getName().equals(regexForSearch)) {
                contacts.add(contact);
            }
        }
        return contacts;
    }

    private String makePaginatedHeader(int page_number, int total_record_count) {

        String newLine = "\n";
        return "{" +
                newLine + "\"page_number\": " + page_number +
                newLine + "\"page_size\": " + page_size +
                newLine + "\"total_record_count: " + total_record_count + "," +
                newLine + "\"Contacts: " +
                newLine;
    }

    private String makeJson(int page_number, int total_record_count){
        String json = "";
        if (page_number * page_size <= total_record_count) {
            json = new Gson().toJson(makeListForJson(contacts,page_number,total_record_count));
        }
        return json;
    }

    private List<Contact> makeListForJson(List<Contact> fullContacts, int page_number, int total_record_count){
        List<Contact> temp = new LinkedList<Contact>();
        for(int i = page_number*page_size; i<=page_number*page_size+page_size; i++){
            temp.add(fullContacts.get(i));
        }
        return temp;
    }
}