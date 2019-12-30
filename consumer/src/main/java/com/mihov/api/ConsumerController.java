package com.mihov.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Denis on 28-Dec-19.
 *
 * @author Denis
 */
@Controller
public class ConsumerController{

  private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

  private final CountDownLatch latch;
  private final MongoTemplate mongoTemplate;

  @Autowired
  public ConsumerController(CountDownLatch latch, MongoTemplate mongoTemplate) {
    this.latch = latch;
    this.mongoTemplate = mongoTemplate;
  }

  @KafkaListener(topics = "${kafka.topic.name}", groupId = "group1", containerFactory = "kafkaListenerContainerFactory")
  public void listen(@Payload SimpleDto message) {
    logger.debug("Message received :{}", message);

    mongoTemplate.insert(message, "collection1");
    logger.debug("1 message stored in db");

    latch.countDown();
  }
}
