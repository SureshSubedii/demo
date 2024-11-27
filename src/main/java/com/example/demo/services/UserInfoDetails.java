package com.example.demo.services;

import com.example.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfoDetails implements UserDetails {

    private String email; // Changed from 'name' to 'username' for clarity
    private String password;
    private String name;

    
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(User user) {
        this.email = user.getEmail(); // Assuming 'name' is used as 'username'
        this.password = user.getPassword();
        this.name = user.getName();
    //     this.authorities = List.of(User.getRoles().split(","))
    //             .stream()
    //             .map(SimpleGrantedAuthority::new)
    //             .collect(Collectors.toList());
    // 
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
    public String getName() {
        return name;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement your logic if you need this
    }
}