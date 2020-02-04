package com.github.benchdoos.meetroom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRole {
    @Id
    @GeneratedValue
    UUID id;

    @NotBlank
    @Column(unique = true)
    String role;

    @NotBlank
    @Column(unique = true)
    String name;
}
