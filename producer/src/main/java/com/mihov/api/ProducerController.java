package com.mihov.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Denis on 22-Dec-19.
 *
 * @author Denis
 */
@RestController
@RequestMapping("/api/messages")
public class ProducerController{

  private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final String topicName;

  @Autowired
  public ProducerController(
    KafkaTemplate<String, Object> kafkaTemplate,
    @Value(value = "${kafka.topic.name}") String topicName
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.topicName = topicName;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void produceMessage(
    @RequestBody JsonNode message
  ) {
    kafkaTemplate.send(topicName, message);
    logger.debug("Message sent: {}", message);
  }
}
