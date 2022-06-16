package utils;

import logger.Log;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public final class APIUtils {

    private APIUtils() {}

    public static String sendPOST(HttpPost post) {
        Log.info("Send POST: " + post.getRequestUri());
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException | ParseException e) {
            Log.error(post.getRequestUri()+ " ERROR: " + e);
            throw new RuntimeException(e);
        }
    }
}