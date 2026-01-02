package com.kalanblow.school_management.model;

import com.kalanblow.school_management.model.validation.ValidationGroupOne;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserName implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{notnull.message}")
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String firstName;

    @NotNull(message = "{notnull.message}")
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String lastName;

    public String getFullName() {

        return String.format("%s %s", firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
