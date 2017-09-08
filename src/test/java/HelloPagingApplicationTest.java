import hello.HelloPagingApplication;
import hello.controller.WebController;
import hello.model.Contact;
import hello.model.ErrorJson;
import hello.service.ContactService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@SpringBootTest(classes = HelloPagingApplication.class)

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration

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
    public void testControllerSearch() throws Exception {
        contactService.save(new Contact("test"));
        //assertThat(controller.findByKey("(test)",0)).isNotEmpty();
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
        List<Contact> contacts = restTemplate.getForObject("http://localhost:8080/hello/contacts?nameFilter=(test)", List.class);
        assertNotNull(contacts);
      //  assertTrue(contacts.get(0).toString().matches("^.[{id=].*[,name=test}]"));
    }

}


