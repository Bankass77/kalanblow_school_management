package com.kalanblow.school_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.kalanblow.school_management.model.enums.TypeParent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Table(name = "parent")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Parent implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @Embedded
    private  User user;

    @NotNull(message = "{notnull.message}")
    private String profession;

    @NotNull(message = "{notnull.message}")
    @Enumerated(EnumType.STRING)
    private TypeParent typeParent;

    @JsonManagedReference
    @OneToMany(mappedBy = "parents", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Student> enfants = new HashSet<>();
}
