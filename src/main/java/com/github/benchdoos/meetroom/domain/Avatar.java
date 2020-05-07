package com.github.benchdoos.meetroom.domain;

import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Entity that stores user's avatar
 */
@Entity
@Table(name = "users_avatars")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Avatar {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    /**
     * Type of stored data.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type",length = 36)
    private AvatarDataType type;

    /**
     * Avatar data. In case of {@link AvatarDataType#BASE64} it can be base64 string.
     * In case of {@link AvatarDataType#GRAVATAR} can be email address.
     */
    @Column(name = "data", length = 98304)
    private String data;
}
