package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.framework", "com.blog"})
@MapperScan("com.framework.mapper")
public class BackendBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendBlogApplication.class, args);
    }
}
