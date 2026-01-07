package com.kalanblow.school_management.model.shared;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.util.Assert;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class PhoneNumber implements Serializable {

    @Pattern(regexp = "^(\\+223|00223)?[67]\\d{7}$")
    private String phoneNumber;

    protected PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber) {
        Assert.hasText(phoneNumber, "Le numéro de téléphone est obligatoire");
        this.phoneNumber = phoneNumber;
    }

    public String asString() {
        return phoneNumber;
    }
}
