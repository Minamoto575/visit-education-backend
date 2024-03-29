package cn.krl.visiteducationbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
@EnableAsync
public class VisitEducationBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisitEducationBackendApplication.class, args);
    }
}
