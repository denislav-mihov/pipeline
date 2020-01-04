package com.mihov.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.kafka.test.utils.KafkaTestUtils.getSingleRecord;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Denis on 22-Dec-19.
 *
 * @author Denis
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka(ports = 9092, topics = "test-pipeline", partitions = 1)
public class ProducerControllerTest{

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EmbeddedKafkaBroker embeddedKafkaBroker;

  @WithMockUser(value = "user1", password = "pass123")
  @Test
  public void testProduceMessage_OK() throws Exception {

    Vehicle message = new Vehicle("car", "Audi", "A4", 85000, new BigDecimal("17000"), "EUR");

    // produce a message
    mockMvc.perform(
      post("/api/messages")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(message))
    )
      .andExpect(status().isCreated());

    // consume the message
    final Consumer<String, Object> consumer = buildConsumer();

    embeddedKafkaBroker.consumeFromEmbeddedTopics(consumer, "test-pipeline");
    final ConsumerRecord<String, Object> record = getSingleRecord(consumer, "test-pipeline", 1000);

    // Check that output is the same as input
    Assert.assertEquals(record.value(), message);
  }

  private Consumer<String, Object> buildConsumer() {

    final Map<String, Object> consumerProps = KafkaTestUtils
      .consumerProps("testMetricsEncodedAsSent", "true", embeddedKafkaBroker);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
    jsonDeserializer.addTrustedPackages("*");

    return new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), jsonDeserializer)
      .createConsumer();
  }
}