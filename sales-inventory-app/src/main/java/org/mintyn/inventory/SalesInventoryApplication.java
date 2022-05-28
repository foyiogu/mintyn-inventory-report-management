package org.mintyn.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SalesInventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalesInventoryApplication.class,args);
    }

}
