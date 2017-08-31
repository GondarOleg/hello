import hello.HelloPagingApplication;
import hello.controller.WebController;
import hello.model.Contact;
import hello.service.ContactService;
import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloPagingApplication.class)
public class HelloPagingApplicationTest {
    @Autowired
    private WebController controller;
    @Autowired
    private ContactService contactService;

    private MockMvc mockMvc;

    @Before
    public void setUp(){

        contactService.deleteAll();
        this.mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void findContactMustReturnJSON() throws Exception {

    }

    @Test
    public void contexLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testControllerSearch() {
        contactService.save(new Contact("test1"));
        assertThat(controller.findAll("(test)")).isNotEmpty();
    }

    @Test
    public void testSearchResultEqualRegexNotReturned() {
        contactService.deleteAll();
        contactService.save(new Contact("^.*[aei].*$"));
        assertThat(controller.findAll("^.*[aei].*$")).isEqualToIgnoringCase("contacts: []");
    }

    @Test
    public void testContactClass(){
        Contact contact = new Contact("test");
        assertThat(contact.getName()).matches("test");
    }


}


