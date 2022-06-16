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
public class Account {

    private final int id;
    private final String first_name;
    private final String last_name;

    public Account(@JsonProperty(value= "id") int id,
                   @JsonProperty(value= "first_name") String first_name,
                   @JsonProperty(value= "last_name") String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
