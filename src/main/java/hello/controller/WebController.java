package hello.controller;

import hello.model.Contact;
import hello.exception.ErrorInRegexpException;
import hello.utils.JsonMaker;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import hello.service.ContactService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.registry.InvalidRequestException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


@RestController
@RequestMapping("/hello")
public class WebController {

    @Autowired
    private ContactService contactService;

    @Value("${page_size}")
    private int pageSize;

    @RequestMapping("/save")
    public HttpStatus save() {
//        contactService.save(new Contact("Contact1"));
//        contactService.save(new Contact("Contact2"));
//        contactService.save(new Contact("Contact3"));
//        contactService.save(new Contact("Contact4"));
//        contactService.save(new Contact("Contact5"));
//        contactService.save(new Contact("Contact6"));
        contactService.save(new Contact("test"));
 //      contactService.save(new Contact("^.*[aei].*$"));
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public @ResponseBody
    String findByKey(@RequestParam(value = "nameFilter") String regex, @RequestParam(value = "page", required = false) Integer pageNum, HttpServletRequest request) throws NotFoundException, ErrorInRegexpException {
        int pageNumber = 0;
        int totalPages = 0;
        int totalRecordCount = 0;
        try {
            Pattern pattern = Pattern.compile(regex);
            List<Contact> contacts = contactService.findContactsByRegex(pattern);
            if (CollectionUtils.isEmpty(contacts)) {
                return "No contacts found!";
            }
            totalRecordCount = contacts.size();
            totalPages = (totalRecordCount % pageSize) == 0 ? totalRecordCount / pageSize : totalRecordCount / pageSize + 1;
            if (pageNum != null) {
                if (pageNum > totalPages || pageNum == 0) {
                    throw new NotFoundException("Page not found", new NotFoundException("Page index out of range!!!"));
                }
                pageNumber = pageNum - 1;
            }
            return JsonMaker.makePaginatedJson(totalPages, pageNumber, pageSize, totalRecordCount, regex, request.getRequestURL().toString(), contacts);
        } catch (PatternSyntaxException ex) {
            throw new ErrorInRegexpException(ex.getDescription());
        }
    }

    @RequestMapping("/find_all")
    public @ResponseBody
    List<Contact> findAll() {
        return contactService.findAllContacts();
    }

    @RequestMapping("/delete_all")
    public HttpStatus delete() {
        contactService.deleteAll();
        return HttpStatus.OK;
    }

}