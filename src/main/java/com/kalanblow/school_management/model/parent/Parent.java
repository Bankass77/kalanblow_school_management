package com.kalanblow.school_management.model.parent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.kalanblow.school_management.model.shared.User;
import com.kalanblow.school_management.model.shared.enums.TypeParent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.io.Serializable;


@Table(name = "parent")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Parent implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @Embedded
    private User user;

    @NotNull(message = "{notnull.message}")
    private String profession;

    @NotNull(message = "{notnull.message}")
    @Enumerated(EnumType.STRING)
    private TypeParent typeParent;
}
