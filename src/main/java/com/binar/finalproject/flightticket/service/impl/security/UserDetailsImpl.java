package com.binar.finalproject.flightticket.service.impl.security;


import com.binar.finalproject.flightticket.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private UUID userId;

    private String fullName;

    private String email;
    private String telephone;
    @JsonIgnore
    private String password;
    private LocalDate birthDate;
    private Boolean gender;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Users users){
        List<SimpleGrantedAuthority> authorities = users.getRolesUsers().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName()))
                .toList();

        return UserDetailsImpl.builder()
                .userId(users.getUserId())
                .email(users.getEmail())
                .fullName(users.getFullName())
                .telephone(users.getTelephone())
                .password(users.getPassword())
                .birthDate(users.getBirthDate())
                .gender(users.getGender())
                .authorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
