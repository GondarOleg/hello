import hello.HelloApplication;
import hello.controller.WebController;
import hello.model.Contact;
import hello.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HelloApplication.class)
@WebMvcTest(controllers = WebController.class)
public class HelloApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Test
    public void testRequestShouldReturnMessageFromService() throws Exception {
        List<Contact> contactList = new LinkedList<Contact>();
        contactList.add(new Contact("test"));
        when(contactService.findAllContacts()).thenReturn(contactList);
        this.mockMvc.perform(get("/hello/contacts?nameFilter=^.*")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }
}
