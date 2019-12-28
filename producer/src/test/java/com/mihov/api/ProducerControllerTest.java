package com.mihov.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
public class ProducerControllerTest{

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @WithMockUser(value = "user1", password = "pass123")
  @Test
  public void testProduceMessage() throws Exception {

    String input = "{\"firstName\": \"John\",\"lastName\": \"Smith\",\"age\": 25,\"address\": {\"streetAddress\": \"21 2nd Street\",\"city\": \"New York\",\"state\": \"NY\", \"postalCode\": 10021}}";

    mockMvc.perform(
      post("/api/messages")
        .contentType(MediaType.APPLICATION_JSON)
        .content(input)
    )
      .andExpect(status().isCreated());
  }
}