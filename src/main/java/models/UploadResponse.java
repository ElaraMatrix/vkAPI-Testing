package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class UploadResponse {

    private String server;
    private String photo;
    private String hash;

    public UploadResponse(@JsonProperty(value= "server") String server,
                          @JsonProperty(value= "photo") String photo,
                          @JsonProperty(value= "hash") String hash) {
        this.server = server;
        this.photo = photo;
        this.hash = hash;
    }
}
