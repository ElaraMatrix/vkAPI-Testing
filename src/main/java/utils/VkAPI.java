package utils;

import aquality.selenium.core.utilities.JsonSettingsFile;
import logger.Log;
import models.Account;
import models.Photo;
import models.UploadResponse;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import utils.parameters.APIMethod;
import utils.parameters.APIMethodParameter;
import utils.parameters.JsonResponseValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class VkAPI {

    private final static String apiUrl = new JsonSettingsFile("settings.json").getValue("/api_url").toString();
    private final static BasicNameValuePair token = new BasicNameValuePair(APIMethodParameter.ACCESS_TOKEN.toString(), new JsonSettingsFile("credentials.json").getValue("/token").toString());
    private final static BasicNameValuePair version = new BasicNameValuePair(APIMethodParameter.VERSION.toString(), new JsonSettingsFile("settings.json").getValue("/vk_api_version").toString());

    private VkAPI() {}

    public static List<Photo> getAllPhotos() {
        Log.info("Get all photos");
        HttpPost post = new HttpPost(apiUrl + APIMethod.PHOTOS_GET_ALL);
        post.setEntity(new UrlEncodedFormEntity(new ArrayList<>() {{
            add(token);
            add(version);
        }}));
        String response = APIUtils.sendPOST(post);
        return JsonReader.getPhotos(response);
    }

    public static Account getAccount() {
        Log.info("Get account");
        HttpPost post = new HttpPost(apiUrl + APIMethod.ACCOUNT_GET_PROFILE);
        post.setEntity(new UrlEncodedFormEntity(new ArrayList<>() {{
            add(token);
            add(version);
        }}));
        String response = APIUtils.sendPOST(post);
        return JsonReader.getAccount(response);
    }

    public static String createWallPost(String text) {
        Log.info("Create post: " + text);
        HttpPost post = new HttpPost(apiUrl + APIMethod.WALL_POST);
        post.setEntity(new UrlEncodedFormEntity(new ArrayList<>() {{
            add(token);
            add(new BasicNameValuePair(APIMethodParameter.MESSAGE.toString(), text));
            add(version);
        }}));
        String response = APIUtils.sendPOST(post);
        return JsonReader.getResponseValue(response, JsonResponseValue.POST_ID);
    }

    public static String updateWallPost(String postID, String text, Photo photo) {
        Log.info("Update post: " + postID + ", " + text + ", " + photo);
        HttpPost post = new HttpPost(apiUrl + APIMethod.WALL_EDIT);
        post.setEntity(new UrlEncodedFormEntity(new ArrayList<>() {{
            add(token);
            add(new BasicNameValuePair(APIMethodParameter.POST_ID.toString(), postID));
            add(new BasicNameValuePair(APIMethodParameter.MESSAGE.toString(), text));
            add(new BasicNameValuePair(APIMethodParameter.ATTACHMENTS.toString(), "photo" + photo.getOwner_id() + "_" + photo.getId()));
            add(version);
        }}));
        String response = APIUtils.sendPOST(post);
        return JsonReader.getResponseValue(response, JsonResponseValue.POST_ID);
    }

    public static String deleteWallPost(String postID) {
        Log.info("Delete post: " + postID);
        HttpPost post = new HttpPost(apiUrl + APIMethod.WALL_DELETE);
        post.setEntity(new UrlEncodedFormEntity(new ArrayList<>() {{
            add(token);
            add(new BasicNameValuePair(APIMethodParameter.POST_ID.toString(), postID));
            add(version);
        }}));
        return APIUtils.sendPOST(post);
    }

    public static String addReplyToWallPost(String postID, String reply) {
        Log.info("Add reply to post: " + postID + ", " + reply);
        HttpPost post = new HttpPost(apiUrl + APIMethod.WALL_CREATE_COMMENT);
        post.setEntity(new UrlEncodedFormEntity(new ArrayList<>() {{
            add(token);
            add(new BasicNameValuePair(APIMethodParameter.POST_ID.toString(), postID));
            add(new BasicNameValuePair(APIMethodParameter.MESSAGE.toString(), reply));
            add(version);
        }}));
        String response = APIUtils.sendPOST(post);
        return JsonReader.getResponseValue(response, JsonResponseValue.REPLY_ID);
    }

    public static boolean isPostLiked(String postID) {
        Log.info("Is post liked by user: " + postID);
        HttpPost post = new HttpPost(apiUrl + APIMethod.LIKES_IS_LIKED);
        post.setEntity(new UrlEncodedFormEntity(new ArrayList<>() {{
            add(token);
            add(new BasicNameValuePair(APIMethodParameter.USER_ID.toString(), String.valueOf(getAccount().getId())));
            add(new BasicNameValuePair(APIMethodParameter.TYPE.toString(), "post"));
            add(new BasicNameValuePair(APIMethodParameter.ITEM_ID.toString(), postID));
            add(version);
        }}));
        String response = APIUtils.sendPOST(post);
        return JsonReader.getResponseValue(response, JsonResponseValue.LIKES_NUMBER).equals("1");
    }

    private static String getWallUploadServer() {
        Log.info("Get wall upload server");
        HttpPost post = new HttpPost(apiUrl + APIMethod.PHOTOS_GET_WALL_UPLOAD_SERVER);
        post.setEntity(new UrlEncodedFormEntity(new ArrayList<>() {{
            add(token);
            add(new BasicNameValuePair(APIMethodParameter.GROUP_ID.toString(), String.valueOf(getAccount().getId())));
            add(version);
        }}));
        String response = APIUtils.sendPOST(post);
        return JsonReader.getResponseValue(response, JsonResponseValue.UPLOAD_URL);
    }

    private static UploadResponse photoTransfer() {
        Log.info("File transfer");
        HttpPost post = new HttpPost(getWallUploadServer());
        File image = new File(System.getProperty("user.dir") + new JsonSettingsFile("settings.json").getValue("/image_path"));
        HttpEntity entity = MultipartEntityBuilder.create().addBinaryBody("photo", image, ContentType.APPLICATION_OCTET_STREAM, image.getName()).build();
        post.setEntity(entity);
        return JsonReader.getUploadResponse(APIUtils.sendPOST(post));
    }

    public static Photo uploadPhoto() {
        Log.info("Upload file");
        HttpPost post = new HttpPost(apiUrl + APIMethod.PHOTOS_SAVE_WALL_PHOTO);
        UploadResponse upload = photoTransfer();
        post.setEntity(new UrlEncodedFormEntity(new ArrayList<>() {{
            add(token);
            add(new BasicNameValuePair(APIMethodParameter.GROUP_ID.toString(), String.valueOf(getAccount().getId())));
            add(new BasicNameValuePair(APIMethodParameter.PHOTO.toString(), upload.getPhoto()));
            add(new BasicNameValuePair(APIMethodParameter.SERVER.toString(), upload.getServer()));
            add(new BasicNameValuePair(APIMethodParameter.HASH.toString(), upload.getHash()));
            add(version);
        }}));
        String response = APIUtils.sendPOST(post);
        return JsonReader.getPhotos(response).get(0);
    }
}