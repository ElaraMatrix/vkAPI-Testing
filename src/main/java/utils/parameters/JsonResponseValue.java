package utils.parameters;

import lombok.Getter;

public enum JsonResponseValue {
    POST_ID ("post_id"),
    LIKES_NUMBER ("liked"),
    UPLOAD_URL ("upload_url"),
    REPLY_ID ("comment_id");

    @Getter
    private String name;

    JsonResponseValue(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
