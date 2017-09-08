package hello.controller;

import com.google.gson.Gson;
import hello.model.Contact;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import hello.service.ContactService;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping("/hello")
public class WebController {

    static final String NEW_LINE = "\n";

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
    String findByKey(@RequestParam(value = "nameFilter") String regex, @RequestParam(value = "page", required = false) Integer page_num, HttpServletRequest request) throws NotFoundException {
        int page_number = 0;
        int total_pages = 0;
        int total_record_count = 0;
        contacts = searchContacts(regex);
        total_record_count = contacts.size();
        if (contacts.size() == 0) {
            return "No contacts found!";
        }
        total_pages = (total_record_count % page_size) == 0 ? total_record_count / page_size : total_record_count / page_size + 1;
        if (page_num != null) {
            if (page_num > total_pages || page_num == 0) {
                throw new NotFoundException("Page not found", new NotFoundException("Page index out of range!!!"));
            }
            page_number = page_num - 1;
        }
        return makePaginatedHeader(total_pages) + makeJson(page_number, total_record_count) + makePaginatedFooter(total_pages, page_number, regex, request.getRequestURL().toString());
    }

    @RequestMapping("/findall")
    public @ResponseBody
    List<Contact> findAll() {
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

    private String makePaginatedHeader(int total_pages) {

        return "{" +
                NEW_LINE + "\"meta\": {" +
                NEW_LINE + "\"total-pages\": " + total_pages +
                NEW_LINE + " }," +
                NEW_LINE + "\"Contacts\" :" +
                NEW_LINE;
    }

    private String makePaginatedFooter(int total_pages, int page_number, String regex, String url) {
        page_number++;
        return NEW_LINE + "\"links\": {" +
                NEW_LINE + "\"self\": \"" + url + "?nameFilter=" + regex + "&page=" + page_number + "\"" +
                NEW_LINE + "\"first\": \"" + url + "?nameFilter=" + regex + "&page=1\"" +
                NEW_LINE + "\"prev\": \"" + url + "?nameFilter=" + regex + "&page=" + ((page_number - 1 == 0) ? page_number : (page_number - 1)) + "\"" +
                NEW_LINE + "\"next\": \"" + url + "?nameFilter=" + regex + "&page=" + ((page_number >= total_pages) ? page_number : (page_number + 1)) + "\"" +
                NEW_LINE + "\"last\": \"" + url + "?nameFilter=" + regex + "&page=" + total_pages + "\"" +
                NEW_LINE + "}";
    }

    private String makeJson(int page_number, int total_record_count) {
        String json = "";
        json = new Gson().toJson(makeListForJson(page_number, total_record_count));
        return json;
    }

    private List<Contact> makeListForJson(int page_number, int total_record_count) {
        List<Contact> temp = new LinkedList<Contact>();
        int endIndex = 0;
        if (page_number * page_size + page_size >= total_record_count) {
            endIndex = total_record_count;
        } else {
            endIndex = page_number * page_size + page_size;
        }
        for (int i = page_number * page_size; i < endIndex; i++) {
            temp.add(contacts.get(i));
        }
        return temp;
    }
}