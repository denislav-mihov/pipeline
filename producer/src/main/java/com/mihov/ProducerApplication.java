package com.mihov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Created by Denis on 21-Dec-19.
 *
 * @author Denis
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class, scanBasePackages = "com.mihov")
public class ProducerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ProducerApplication.class, args);
  }
}
