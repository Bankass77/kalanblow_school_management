package com.kalanblow.school_management.model.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalanblow.school_management.model.shared.enums.Gender;
import com.kalanblow.school_management.model.shared.enums.MaritalStatus;
import com.kalanblow.school_management.model.shared.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Embedded
    private UserName userName = new UserName();

    @NotNull(message = "{notnull.message}")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "{notnull.message}")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @CreatedDate
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Nullable
    @Embedded
    @Column(name = "user_phoneNumber", unique = true, nullable = false)
    private PhoneNumber user_phoneNumber = new PhoneNumber();

    @Column
    @Nullable
    private byte[] avatar;

    @NotNull(message = "{notnull.message}")
    @Embedded
    @Column(name = "user_email", unique = true, nullable = false)
    private Email userEmail = new Email();

    @NotNull(message = "{notnull.message}")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "streetNumber", column = @Column(name = "streetNumber")),
            @AttributeOverride(name = "codePostale", column = @Column(name = "codePostale")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "country", column = @Column(name = "country"))
    })
    private Address address;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @NotNull(message = "{notnull.message}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Le mot de passe doit être fort.")
    private String password;

}
