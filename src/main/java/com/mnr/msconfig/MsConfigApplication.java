package com.mnr.msconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

//micro-service de configuration
@EnableConfigServer  //service de configuration
@SpringBootApplication
public class MsConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsConfigApplication.class, args);
    }

}
