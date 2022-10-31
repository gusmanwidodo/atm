package com.github.gusmanwidodo.atm.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.github.gusmanwidodo.atm")
@EnableJpaRepositories("com.github.gusmanwidodo.atm.core.repository")
@EntityScan("com.github.gusmanwidodo.atm.core.model")
public class CliApplication {

    public static void main(String[] args) {
        SpringApplication.run(CliApplication.class, args);
    }

}
