package xyz.towork.TestingTools.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.towork.TestingTools.utils.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class JsonGenController {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonGenController.class);

    //POST
    @PostMapping("/json")
    public ResponseEntity<String> jsonPost(@RequestBody Map<String, Map<String, String>> jsonPlayload) {
        logger.info("Recevied JSON: {}", jsonPlayload);

        Map<String, Object> results = new LinkedHashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        String fileName = "data.json";

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());

        try {
            for (Map.Entry<String, Map<String,String>> topEntry : jsonPlayload.entrySet()) {
                String topKey = topEntry.getKey();
                Map<String, String> nestedMap = topEntry.getValue();
                String nestedKey = "";
                String nestedValue = "";

                for (Map.Entry<String, String> nestedEntry : nestedMap.entrySet()) {
                    nestedKey = nestedEntry.getKey();
                    nestedValue = nestedEntry.getValue();
                }

                logger.info("Top key: {}; Nested key: {}; Nested value: {};",topKey, nestedKey, nestedValue);

                switch (nestedKey) {
                    case "string": 
                        results.put(topKey, JsonUtil.generateRandomString(nestedValue));
                        break;
                    case "phone":
                        results.put(topKey, JsonUtil.generateRandomPhoneNumber(nestedValue));
                        break;
                    case "integer":
                        results.put(topKey, JsonUtil.generateRandomInteger(nestedValue));
                        break;
                    case "boolean":
                        results.put(topKey, JsonUtil.generateRandomBoolean(nestedValue));
                        break;
                    case "date":
                        results.put(topKey, JsonUtil.generateNowDate());
                        break;
                    case "email":
                        results.put(topKey, JsonUtil.generateRandomEmail());
                        break;
                    default:
                        results.put(topKey, "none");
                        break;
                }
            }
            logger.info("Post JSON: {}", results);
            return new ResponseEntity<>(mapper.writeValueAsString(results), headers, HttpStatus.OK);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Error: converting to json!", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
