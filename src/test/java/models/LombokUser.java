package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokUser {

  private Integer id;
  private String email;
  @JsonProperty("first_name")
  private String firstName;
  @JsonProperty("last_name")
  private String lastName;
  private String avatar;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LombokUser that = (LombokUser) o;
    return id.equals(that.id) &&
           Objects.equals(email, that.email) &&
           Objects.equals(firstName, that.firstName) &&
           Objects.equals(lastName, that.lastName) &&
           Objects.equals(avatar, that.avatar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, firstName, lastName, avatar);
  }
}
