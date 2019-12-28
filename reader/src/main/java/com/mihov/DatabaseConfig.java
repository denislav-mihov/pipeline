package com.mihov;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


/**
 * Created by Denis on 28-Dec-19.
 *
 * @author Denis
 */
@EnableMongoRepositories(basePackages = "com.mihov.api")
@Configuration
public class DatabaseConfig extends AbstractMongoClientConfiguration{

  @Value("${spring.data.mongodb.host}")
  private String host;

  @Value("${spring.data.mongodb.port}")
  private String port;

  @Value("${spring.data.mongodb.database}")
  private String database;

  @Override
  public MongoClient mongoClient() {
    return MongoClients.create(host + ":" + port);
  }

  @Override
  protected String getDatabaseName() {
    return database;
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoClient(), "pipeline");
  }
}
