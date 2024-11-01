package br.com.orben.orbenconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class OrbenConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrbenConfigServerApplication.class, args);
    }

}
