package com.meerkatgramv2post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MeerkatgramV2PostApplication {

  public static void main(String[] args) {
    SpringApplication.run(MeerkatgramV2PostApplication.class, args);
  }

}
