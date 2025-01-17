package com.sda.travelagency.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String authority;

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public Authority() {
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
