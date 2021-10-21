package cn.krl.visiteducationbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
@MapperScan("cn.krl.visiteducationbackend.mapper")

public class VisitEducationBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisitEducationBackendApplication.class, args);
    }

}