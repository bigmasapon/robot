package com.example.robot.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MvcUtil {

    public static JsonNode postData(MockMvc mvc, String url, String content) throws Exception {
        var builder = MockMvcRequestBuilders
                .post(url)
                .contentType("application/json; charset=UTF-8")
                .content(content);

        var body = mvc.perform(builder)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new JsonMapper().readTree(body);
    }
}
