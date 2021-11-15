package com.example.jwt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String permission;

    public PermissionEntity() {
    }

    public PermissionEntity(String permission) {
        this.permission = permission;
    }
}