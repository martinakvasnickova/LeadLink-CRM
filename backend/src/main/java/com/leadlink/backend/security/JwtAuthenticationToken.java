package com.leadlink.backend.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Implementace tokenu pro JWT autentizaci.
 * Obsahuje informace o uživateli (principal) a jeho přihlašovacích údajích (credentials).
 */

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;
    private final String credentials;

    public JwtAuthenticationToken(UserDetails principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
