package com.mihov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Created by Denis on 28-Dec-19.
 *
 * @author Denis
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class, scanBasePackages = "com.mihov")
public class ReaderApplication{
  public static void main(String[] args) {
    SpringApplication.run(ReaderApplication.class, args);
  }
}
