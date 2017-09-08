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
    public static final String NAME_FILTER = "?nameFilter=";
    public static final String PAGE = "&page=";

    @Autowired
    private ContactService contactService;

    private List<Contact> contacts = new LinkedList<Contact>();
    @Value("${page_size}")
    private int pageSize;


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
    String findByKey(@RequestParam(value = "nameFilter") String regex, @RequestParam(value = "page", required = false) Integer pageNum, HttpServletRequest request) throws NotFoundException {
        int pageNumber = 0;
        int totalPages = 0;
        int totalRecordCount = 0;
        searchContacts(regex);
        totalRecordCount = contacts.size();
        if (contacts.isEmpty()) {
            return "No contacts found!";
        }
        totalPages = (totalRecordCount % pageSize) == 0 ? totalRecordCount / pageSize : totalRecordCount / pageSize + 1;
        if (pageNum != null) {
            if (pageNum > totalPages || pageNum == 0) {
                throw new NotFoundException("Page not found", new NotFoundException("Page index out of range!!!"));
            }
            pageNumber = pageNum - 1;
        }
        return makePaginatedHeader(totalPages) + makeJson(pageNumber, totalRecordCount) + makePaginatedFooter(totalPages, pageNumber, regex, request.getRequestURL().toString());
    }

    @RequestMapping("/find_all")
    public @ResponseBody
    List<Contact> findAll() {
        return contactService.findAllContacts();
    }

    private void searchContacts(String regexForSearch) {
        for (Contact contact : contactService.findAllContacts()) {
            if (contact.getName().matches(regexForSearch) && !contact.getName().equals(regexForSearch)) {
                contacts.add(contact);
            }
        }
    }

    private String makePaginatedHeader(int totalPages) {

        return "{" +
                NEW_LINE + "\"meta\": {" +
                NEW_LINE + "\"total-pages\": " + totalPages +
                NEW_LINE + " }," +
                NEW_LINE + "\"Contacts\" :" +
                NEW_LINE;
    }

    private String makePaginatedFooter(int totalPages, int pageNumber, String regex, String url) {
        pageNumber++;
        return NEW_LINE + "\"links\": {" +
                NEW_LINE + "\"self\": \"" + url + NAME_FILTER + regex + PAGE + pageNumber + "\"" +
                NEW_LINE + "\"first\": \"" + url + NAME_FILTER + regex + PAGE + "1\"" +
                NEW_LINE + "\"prev\": \"" + url + NAME_FILTER + regex + PAGE + ((pageNumber - 1 == 0) ? pageNumber : (pageNumber - 1)) + "\"" +
                NEW_LINE + "\"next\": \"" + url + NAME_FILTER + regex + PAGE + ((pageNumber >= totalPages) ? pageNumber : (pageNumber + 1)) + "\"" +
                NEW_LINE + "\"last\": \"" + url + NAME_FILTER + regex + PAGE + totalPages + "\"" +
                NEW_LINE + "}";
    }

    private String makeJson(int pageNumber, int totalRecordCount) {
        return new Gson().toJson(makeListForJson(pageNumber, totalRecordCount));
    }

    private List<Contact> makeListForJson(int pageNumber, int totalRecordCount) {
        List<Contact> temp = new LinkedList<Contact>();
        int endIndex = 0;
        if (pageNumber * pageSize + pageSize >= totalRecordCount) {
            endIndex = totalRecordCount;
        } else {
            endIndex = pageNumber * pageSize + pageSize;
        }
        for (int i = pageNumber * pageSize; i < endIndex; i++) {
            temp.add(contacts.get(i));
        }
        return temp;
    }
}