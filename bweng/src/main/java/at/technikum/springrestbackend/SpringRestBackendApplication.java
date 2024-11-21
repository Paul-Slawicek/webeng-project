package at.technikum.springrestbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:8081")
@SpringBootApplication
public class SpringRestBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestBackendApplication.class, args);
    }

}
