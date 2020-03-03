package com.github.benchdoos.meetroom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotBlank
    @Column(name = "username", unique = true)
    @Size(min = 4, max = 16)
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

//    @Column(name = "email")
//    private String email;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled;

    @NotEmpty
    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<UserRole> roles;

}
