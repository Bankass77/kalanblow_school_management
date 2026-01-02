package com.kalanblow.school_management.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalanblow.school_management.model.enums.Gender;
import com.kalanblow.school_management.model.enums.MaritalStatus;
import com.kalanblow.school_management.model.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@Data
@Getter
@Setter
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

    public UserName getUserName() {
        return userName;
    }

    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Nullable
    public PhoneNumber getUser_phoneNumber() {
        return user_phoneNumber;
    }

    public void setUser_phoneNumber(@Nullable PhoneNumber user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }

    @Nullable
    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable byte[] avatar) {
        this.avatar = avatar;
    }

    public Email getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Email userEmail) {
        this.userEmail = userEmail;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
