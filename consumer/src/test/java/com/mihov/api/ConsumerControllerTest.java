package com.mihov.api;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Denis on 29-Dec-19.
 *
 * @author Denis
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka(ports = 9092, topics = "test-pipeline", partitions = 1)
public class ConsumerControllerTest{

  @Autowired
  private EmbeddedKafkaBroker embeddedKafkaBroker;

  @Autowired
  private CountDownLatch latch;

  @WithMockUser(value = "user1", password = "pass123")
  @Test
  public void testProduceMessage_OK() throws Exception {

    // Produce a message
    SimpleDto message = new SimpleDto("Paul", "ADMIN");

    Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
    Producer<String, Object> producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new JsonSerializer()).createProducer();

    producer.send(new ProducerRecord<>("test-pipeline", message));
    producer.flush();

    // Check that the consumer thread is free after finishing the job
    assertThat(latch.await(10L, TimeUnit.SECONDS)).isTrue();
  }
}
