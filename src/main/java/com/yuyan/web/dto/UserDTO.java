package com.yuyan.web.dto;

import java.util.List;

public class UserDTO {

    private String email;
    private String name;
    private String password;
    private String enabled;
    private Long id;
    private List<String> role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> roles) {
        this.role = roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
