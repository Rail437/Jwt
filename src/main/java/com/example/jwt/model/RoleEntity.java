package com.example.jwt.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(name = "roles_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionEntity> permission;

    public RoleEntity(String name, Set<PermissionEntity> permission) {
        this.name = name;
        this.permission = permission;
    }

    public RoleEntity() {

    }
    @Override
    public String getAuthority() {
        return name;
    }

}
