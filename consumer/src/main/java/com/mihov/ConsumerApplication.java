package com.mihov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Denis on 28-Dec-19.
 *
 * @author Denis
 */
@SpringBootApplication(scanBasePackages = "com.mihov")
public class ConsumerApplication{
  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }
}
