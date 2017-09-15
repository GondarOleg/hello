import classesfortests.HttpServletRequestForTest;
import hello.HelloApplication;
import hello.controller.WebController;
import hello.model.Contact;
import hello.model.ErrorJson;
import hello.service.ContactService;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.management.BadAttributeValueExpException;
import javax.servlet.ServletException;
import javax.xml.registry.InvalidRequestException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = HelloApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)


public class HelloApplicationTest {

    @Autowired
    private WebController controller;
    @Autowired
    private ContactService contactService;

    HttpServletRequestForTest requestForTest = new HttpServletRequestForTest();

    @Before
    public void setUp() {
        contactService.deleteAll();
        contactService.save(new Contact("test"));
    }

    @Test
    public void testContextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testControllerSearch() throws Exception {
        contactService.save(new Contact("test"));
        assertThat(controller.findByKey("[test]", 1, requestForTest)).isNotEmpty();
    }

    @Test
    public void testSearchResultEqualRegexNotReturned() throws NotFoundException, InvalidRequestException {
        contactService.deleteAll();
        contactService.save(new Contact("^.*[aei].*$"));
        assertThat(controller.findByKey("^.*[aei].*$", 1, requestForTest)).isEqualToIgnoringCase("No contacts found!");
    }

    @Test
    public void testContactClass() {
        Contact contact = new Contact("test");
        assertThat(contact.getName()).matches("test");
    }

    @After
    public void deleteAllTestDataInDB() {
        contactService.deleteAll();
    }
}


