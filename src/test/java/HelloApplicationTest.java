
import hello.HelloApplication;
import hello.controller.WebController;
import hello.model.Contact;
import hello.service.ContactService;
import javassist.NotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.registry.InvalidRequestException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = HelloApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)


public class HelloApplicationTest {

    @Autowired
    private WebController controller;
    @Autowired
    private ContactService contactService;

    private MockMultipartHttpServletRequest mockRequest;

    @Before
    public void init() {
        mockRequest = new MockMultipartHttpServletRequest();
        mockRequest.setMethod("GET");
        mockRequest.setRequestURI("testURI");
    }


    @Test
    public void testContextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testSearchResultEqualRegexNotReturned() throws NotFoundException, InvalidRequestException {
        contactService.deleteAll();
        contactService.save(new Contact("^.*[aei].*$"));
        assertThat(controller.findByKey("^.*[aei].*$", 1, mockRequest)).isEqualToIgnoringCase("No contacts found!");
    }

    @Test
    public void testControllerSearch() throws Exception {
        assertThat(controller.findByKey("^.*", 1, mockRequest)).isNotEmpty();
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


