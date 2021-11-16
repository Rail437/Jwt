package com.example.jwt.model;

import com.example.jwt.auth.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    private String token;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> grantedAuthorities;

    public UserEntity(String username,
                      String password,
                      boolean isAccountNonExpired,
                      boolean isAccountNonLocked,
                      boolean isCredentialsNonExpired,
                      boolean isEnabled,
                      Set<RoleEntity> grantedAuthorities) {
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public UserEntity(String username,
                      String password,
                      boolean isAccountNonExpired,
                      boolean isAccountNonLocked,
                      boolean isCredentialsNonExpired,
                      boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public UserEntity() {
    }

    public static UserEntity valueOf(ApplicationUser user){
        return new UserEntity(
                user.getUsername(),
                user.getPassword(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled()
                );
    }
}
