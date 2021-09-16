package cn.krl.visiteducationbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.krl.visiteducationbackend.mapper")
public class SystemBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemBackendApplication.class, args);
    }

}
