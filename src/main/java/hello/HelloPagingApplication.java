package hello;

import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@SpringBootApplication
public class HelloPagingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloPagingApplication.class, args);
    }
}
