package com.github.benchdoos.meetroom.domain;

import com.github.benchdoos.meetroom.domain.annotations.PrivilegeOrRoleName;
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
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "privileges")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Privilege {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @PrivilegeOrRoleName
    @Column(unique = true)
    private String name;

    @Size(max = 1000)
    private String description;
}