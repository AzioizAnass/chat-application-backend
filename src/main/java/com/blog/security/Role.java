package com.blog.security;

public enum Role {
    ADMIN,
    USER;

    public String getAuthority() {
        return "ROLE_" + name();  // Spring Security attend "ROLE_" devant les rôles
    }
}
