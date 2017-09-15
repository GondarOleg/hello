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
import org.springframework.web.util.NestedServletException;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static org.mockito.Matchers.*;
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

    private List<Contact> contactList = new LinkedList<Contact>();

    @Test
    public void testRequestShouldReturnNoContactsFound() throws Exception {
        contactList.clear();
        when(contactService.findContactsByRegex(Pattern.compile("^.*[aei].*$"))).thenReturn(contactList);
        this.mockMvc.perform(get("/hello/contacts?nameFilter=^.*[aei].*$")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("No contacts found!")));
    }

    @Test
    public void testRequestShouldReturnMessageFromService() throws Exception {
        contactList.add(new Contact("test"));
        when(contactService.findContactsByRegex(any(Pattern.class))).thenReturn(contactList);
        this.mockMvc.perform(get("/hello/contacts?nameFilter=(test)")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }

    @Test(expected = NestedServletException.class)
    public void testRequestPage0ShouldThrowException() throws Exception {
        this.mockMvc.perform(get("/hello/contacts?nameFilter=(test)&page=0"));
    }
}
