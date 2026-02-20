package com.khaibut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class KhaibutApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhaibutApplication.class, args);
    }
    public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

}
