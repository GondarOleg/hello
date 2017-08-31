import hello.HelloPagingApplication;
import hello.controller.WebController;
import hello.model.Contact;
import hello.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloPagingApplication.class)
public class HelloPagingApplicationTest {
    @Autowired
    private WebController controller;
    @Autowired
    private ContactService contactService;

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
    public void testSearchResultEqualRegexNotReturned(){
        contactService.deleteAll();
        contactService.save(new Contact("^.*[aei].*$"));
        assertThat(controller.findAll("^.*[aei].*$")).isEqualToIgnoringCase("contacts: []");
    }

    @Test


}


