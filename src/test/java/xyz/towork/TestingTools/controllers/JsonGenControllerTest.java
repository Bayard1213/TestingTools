package xyz.towork.TestingTools.controllers;

import static org.mockito.Mockito.mockStatic;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import xyz.towork.TestingTools.utils.JsonUtil;

@WebMvcTest(JsonGenController.class)
public class JsonGenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    //case "string"
    @Test
    public void testJsonControllerWithString() throws Exception {
        try (MockedStatic<JsonUtil> mockedJsonUtil = mockStatic(JsonUtil.class)) {
            mockedJsonUtil.when (() -> JsonUtil.generateRandomString("5")).thenReturn("randomString");
            
            Map<String, Map<String, String>> jsonPayload = new HashMap<>();
            Map<String, String> nestedMap = new HashMap<>();
            nestedMap.put("string", "5");
            jsonPayload.put("keyString", nestedMap);
            
            String jsonRequest = objectMapper.writeValueAsString(jsonPayload);

            mockMvc.perform(post("/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.keyString").value("randomString"));

            mockedJsonUtil.verify(() -> JsonUtil.generateRandomString("5"), times(1));
        }
    }

    //case "phone"
    @Test
    public void testJsonControllerWithPhoneNumber() throws Exception{
        try (MockedStatic<JsonUtil> mockedJsonUtil = mockStatic(JsonUtil.class)) {
            mockedJsonUtil.when(() -> JsonUtil.generateRandomPhoneNumber("xxx")).thenReturn("randomPhoneNumber");

            Map<String, Map<String, String>> jsonPayload = new HashMap<>();
            Map<String, String> nestedMap = new HashMap<>();
            nestedMap.put("phone", "xxx");
            jsonPayload.put("keyPhoneNumber", nestedMap);

            String jsonRequest = objectMapper.writeValueAsString(jsonPayload);

            mockMvc.perform(post("/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.keyPhoneNumber").value("randomPhoneNumber"));

            mockedJsonUtil.verify(() -> JsonUtil.generateRandomPhoneNumber("xxx"), times(1));
        }
    }

    //case "integer"
    @Test
    public void testJsonControllerWithInteger() throws Exception{
        try (MockedStatic<JsonUtil> mockedJsonUtil = mockStatic(JsonUtil.class)) {
            mockedJsonUtil.when(() -> JsonUtil.generateRandomInteger("1:5")).thenReturn(3);

            Map<String, Map<String, String>> jsonPayload = new HashMap<>();
            Map<String, String> nestedMap = new HashMap<>();
            nestedMap.put("integer", "1:5");
            jsonPayload.put("keyInteger", nestedMap);

            String jsonRequest = objectMapper.writeValueAsString(jsonPayload);

            mockMvc.perform(post("/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.keyInteger").value(3));

            mockedJsonUtil.verify(() -> JsonUtil.generateRandomInteger("1:5"), times(1));
        }
    }

    //case "boolean"
    @Test
    public void testJsonControllerWithBoolean() throws Exception{
        try (MockedStatic<JsonUtil> mockedJsonUtil = mockStatic(JsonUtil.class)) {
            mockedJsonUtil.when(() -> JsonUtil.generateRandomBoolean("false")).thenReturn(false);

            Map<String, Map<String, String>> jsonPayload = new HashMap<>();
            Map<String, String> nestedMap = new HashMap<>();
            nestedMap.put("boolean", "false");
            jsonPayload.put("keyBoolean", nestedMap);

            String jsonRequest = objectMapper.writeValueAsString(jsonPayload);

            mockMvc.perform(post("/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.keyBoolean").value(false));

            mockedJsonUtil.verify(() -> JsonUtil.generateRandomBoolean("false"), times(1));
        }
    }

    //case "date"
    @Test
    public void testJsonControllerWithDate() throws Exception{
        try (MockedStatic<JsonUtil> mockedJsonUtil = mockStatic(JsonUtil.class)) {
            mockedJsonUtil.when(() -> JsonUtil.generateNowDate()).thenReturn("NowDate");

            Map<String, Map<String, String>> jsonPayload = new HashMap<>();
            Map<String, String> nestedMap = new HashMap<>();
            nestedMap.put("date", "");
            jsonPayload.put("keyDate", nestedMap);

            String jsonRequest = objectMapper.writeValueAsString(jsonPayload);

            mockMvc.perform(post("/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.keyDate").value("NowDate"));

            mockedJsonUtil.verify(() -> JsonUtil.generateNowDate(), times(1));
        }
    }
   
    //case "email"
    @Test
    public void testJsonControllerWithEmail() throws Exception{
        try (MockedStatic<JsonUtil> mockedJsonUtil = mockStatic(JsonUtil.class)) {
            mockedJsonUtil.when(() -> JsonUtil.generateRandomEmail()).thenReturn("random@email.com");

            Map<String, Map<String, String>> jsonPayload = new HashMap<>();
            Map<String, String> nestedMap = new HashMap<>();
            nestedMap.put("email", "");
            jsonPayload.put("keyEmail", nestedMap);

            String jsonRequest = objectMapper.writeValueAsString(jsonPayload);

            mockMvc.perform(post("/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.keyEmail").value("random@email.com"));

            mockedJsonUtil.verify(() -> JsonUtil.generateRandomEmail(), times(1));
        }
    }
    
    //case "default"
    @Test
    public void testJsonPostDefaultCase() throws Exception {
        try (MockedStatic<JsonUtil> mockedJsonUtil = mockStatic(JsonUtil.class)) {
            
            Map<String, Map<String, String>> jsonPayload = new HashMap<>();
            Map<String, String> nestedMap = new HashMap<>();
            nestedMap.put("unknownNestedKey", "unknownValue");
            jsonPayload.put("unknownKey", nestedMap);

            String jsonRequest = objectMapper.writeValueAsString(jsonPayload);

            mockMvc.perform(post("/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.unknownKey").value("none"));

            mockedJsonUtil.verifyNoInteractions();
        }
    }

    //проверка на невалидный формат
    @Test
    public void testInvalidJson() throws Exception {
    String invalidJson = "Invalid JSON format";

    mockMvc.perform(post("/json")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidJson))
            .andExpect(status().isBadRequest()); 
    }    
}
