package com.github.benchdoos.meetroom.domain;

import com.github.benchdoos.meetroom.domain.annotations.Username;
import com.github.benchdoos.meetroom.domain.annotations.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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

    @Username
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

    /**
     * User avatar
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private Avatar avatar;

    @Email
    @Column(name = "email", unique = true)
    @Size(min = 4, max = 320)
    private String email;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "need_activation", nullable = false, columnDefinition = "boolean default false")
    private boolean needActivation;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Collection<Role> roles;
}
