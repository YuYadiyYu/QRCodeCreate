package com.yuyadiyu.template1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.yuyadiyu.template1.dao")
public class Template1Application {

    public static void main(String[] args) {
        SpringApplication.run(Template1Application.class, args);
    }

}
