package com.mihov.api;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Denis on 28-Dec-19.
 *
 * @author Denis
 */
@RestController
@RequestMapping("/api/messages")
public class ReaderController{

  private final MongoOperations mongoTemplate;

  public ReaderController(MongoOperations mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @GetMapping
  public List<SimpleDto> getMessages() {
    return mongoTemplate.findAll(SimpleDto.class, "collection1");
  }
}
