package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import logger.Log;
import models.Account;
import models.Photo;
import models.UploadResponse;
import utils.parameters.JsonResponseValue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class JsonReader {

    private JsonReader() {}

    public static String getResponseValue(String response, JsonResponseValue value) {
        Log.info("Get value from json: " + value);
        try {
            JsonNode node = new ObjectMapper().readTree(response);
            return node.at("/response/" + value).asText();
        } catch (IOException e) {
            throw new RuntimeException("Response error: " + e);
        }
    }

    public static Account getAccount(String response) {
        Log.info("Get account from " + response);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(response);
            JsonNode post = node.at("/response");
            return mapper.convertValue(post, Account.class);
        } catch (IOException e) {
            throw new RuntimeException("Get user account error: " + e);
        }
    }

    public static List<Photo> getPhotos(String response) {
        Log.info("Get photo from " + response);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(response);
            JsonNode photo = node.at("/response");
            return Arrays.asList(mapper.readValue(photo.toString(), Photo[].class));
        } catch (IOException e) {
            throw new RuntimeException("Get photo error: " + e);
        }
    }

    public static UploadResponse getUploadResponse(String response) {
        Log.info("Get upload response object");
        try {
            return new ObjectMapper().readValue(response, UploadResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}