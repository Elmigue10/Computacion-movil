package dev.miguel.backendmobile.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileDTO {

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Password")
    private String password;

}
