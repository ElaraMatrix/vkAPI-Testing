package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {

    private final int album_id;
    private final int date;
    private final int id;
    private final int owner_id;

    public Photo(@JsonProperty(value= "album_id") int album_id,
                 @JsonProperty(value= "date") int date,
                 @JsonProperty(value= "id") int id,
                 @JsonProperty(value= "owner_id") int owner_id) {
        this.album_id = album_id;
        this.date = date;
        this.id = id;
        this.owner_id = owner_id;
    }
}