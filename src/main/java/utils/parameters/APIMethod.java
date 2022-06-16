package utils.parameters;

import lombok.Getter;

public enum APIMethod {
    ACCOUNT_GET_PROFILE ("account.getProfileInfo"),
    PHOTOS_GET_ALL ("photos.getAll"),
    PHOTOS_GET_WALL_UPLOAD_SERVER ("photos.getWallUploadServer"),
    PHOTOS_SAVE_WALL_PHOTO ("photos.saveWallPhoto"),
    WALL_POST ("wall.post"),
    WALL_EDIT ("wall.edit"),
    WALL_DELETE ("wall.delete"),
    WALL_CREATE_COMMENT ("wall.createComment"),
    LIKES_IS_LIKED ("likes.isLiked");

    @Getter
    private String name;

    APIMethod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
