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

    @Enumerated(EnumType.STRING)
    @Column(name = "type",length = 36)
    private AvatarDataType type;

    @Column(name = "data", length = 32768)
    private String data;
}