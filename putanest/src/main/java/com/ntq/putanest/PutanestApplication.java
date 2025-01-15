package com.ntq.putanest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Collections;

//@SpringBootApplication
//public class PutanestApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(PutanestApplication.class, args);
//    }
//
//}

@SpringBootApplication
public class PutanestApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PutanestApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", System.getenv("PORT")));
        app.run(args);
    }
}
