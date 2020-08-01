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
import java.io.Serializable;
import java.util.UUID;

/**
 * User settings
 */
@Entity
@Table(name = "user_settings")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserSettings implements Serializable {

    private static final long serialVersionUID = 7876941876322555186L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    /**
     * Show user email to other users on user page. Do not effect on admin users.
     */
    @Column(name = "show_email_to_other_users",
            columnDefinition = "boolean default true",
            nullable = false)
    private Boolean showEmailToOtherUsers = true;

    /**
     * Returns true if dark mode is enabled for user
     */
    @Column(name = "dark_mode_enabled",
            columnDefinition = "boolean default false",
            nullable = false)
    private Boolean darkModeEnabled = false;
}
