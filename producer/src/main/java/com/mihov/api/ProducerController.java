package com.mihov.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

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

  /**
   *
   * @param message The input message of type Vehicle
   * @param result It is only used to test that in case of incorrect input the PreAuthorize validation will be
   *                      triggered before throwing an exception from the bean validation. Easiest to test with @PreAuthorize("false")
   */
  @PreAuthorize("true")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void produceMessage(
    @RequestBody @Validated Vehicle message
    , BindingResult result
  ) {
    /* This if statements does not have any relation with the message producing. Just testing the BindingResult object */
    if (result.hasErrors()) {
      throw new ValidationException("Problem with the input!");
    }

    /* Below starts the essential message producer code */

    ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, message);

    future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>(){

      @Override
      public void onFailure(Throwable throwable) {
        logger.error("Fail to send message: {} due to: {}", message, throwable);
      }

      @Override
      public void onSuccess(SendResult<String, Object> sendResult) {
        logger.debug("Message sent: {} with offset: {}", message, sendResult.getRecordMetadata().offset());
      }
    });
  }
}
