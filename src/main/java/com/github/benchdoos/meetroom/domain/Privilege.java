package com.github.benchdoos.meetroom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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

    private String name;
}