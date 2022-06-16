package utils.parameters;

import lombok.Getter;

public enum APIMethodParameter {
    ACCESS_TOKEN ("access_token"),
    POST_ID ("post_id"),
    GROUP_ID ("group_id"),
    MESSAGE ("message"),
    ATTACHMENTS ("attachments"),
    USER_ID ("user_id"),
    ITEM_ID ("item_id"),
    TYPE ("type"),
    PHOTO ("photo"),
    SERVER ("server"),
    HASH ("hash"),
    VERSION ("v");

    @Getter
    private String name;

    APIMethodParameter(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
