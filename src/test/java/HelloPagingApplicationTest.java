import hello.HelloPagingApplication;
import hello.controller.WebController;
import hello.model.Contact;
import hello.service.ContactService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloPagingApplication.class)
public class HelloPagingApplicationTest {
    @Autowired
    private WebController controller;
    @Autowired
    private ContactService contactService;

    @Before
    public void setUp() {
        contactService.deleteAll();
        contactService.save(new Contact("test"));
    }

    @Test
    public void contexLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testControllerSearch() {

        assertThat(controller.findByKey("(test)", null)).isNotEmpty();
    }

    @Test
    public void testSearchResultEqualRegexNotReturned() {
//        contactService.deleteAll();
//        contactService.save(new Contact("^.*[aei].*$"));
//        assertThat(controller.findByKey("^.*[aei].*$")).isEqualToIgnoringCase("contacts: []");
    }

    @Test
    public void testContactClass() {
        Contact contact = new Contact("test");
        assertThat(contact.getName()).matches("test");
    }

    @Test
    public void integrationTest() {
        RestTemplate restTemplate = new RestTemplate();
        List<Object> contacts = restTemplate.getForObject("http://localhost:8080/hello/contacts?nameFilter=(test)", ArrayList.class);
        assertNotNull(contacts);
        assertTrue(contacts.get(0).toString().matches("^.[{id=].*[,name=test}]"));
    }

}


