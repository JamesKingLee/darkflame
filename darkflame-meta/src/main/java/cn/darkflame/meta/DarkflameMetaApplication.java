package cn.darkflame.meta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.darkflame.meta.*"})
public class DarkflameMetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarkflameMetaApplication.class, args);
    }

}
