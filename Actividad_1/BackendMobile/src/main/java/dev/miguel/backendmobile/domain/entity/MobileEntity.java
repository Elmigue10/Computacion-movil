package dev.miguel.backendmobile.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mobile_user")
public class MobileEntity {

    @Column(name = "full_name")
    private String fullName;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

}
