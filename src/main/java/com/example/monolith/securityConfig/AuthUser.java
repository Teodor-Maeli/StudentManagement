package com.example.monolith.securityConfig;

import com.example.monolith.entity.Student;
import com.example.monolith.entity.Teacher;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Data
public class AuthUser implements UserDetails {

    private String userName;
    private String password;
    private boolean active;
    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;
    private Set<GrantedAuthority> authorities;

    public AuthUser(Student user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.active = user.isActive();

        this.isAccountNonExpired = user.isAccountNonExpired();
        this.isAccountNonLocked = user.isAccountNonLocked();
        this.isCredentialsNonExpired = user.isCredentialsNonExpired();
        this.isEnabled = user.isEnabled();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public AuthUser(Teacher user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.active = user.isActive();

        this.isAccountNonExpired = user.isAccountNonExpired();
        this.isAccountNonLocked = user.isAccountNonLocked();
        this.isCredentialsNonExpired = user.isCredentialsNonExpired();
        this.isEnabled = user.isEnabled();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return userName;
    }


}
