import hello.HelloPagingApplication;
import hello.controller.WebController;
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

    @Test
        public void contexLoads(){
            assertThat(controller).isNotNull();
        }
    }


