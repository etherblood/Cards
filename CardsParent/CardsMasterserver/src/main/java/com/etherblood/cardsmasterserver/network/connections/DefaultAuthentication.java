package com.etherblood.cardsmasterserver.network.connections;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 *
 * @author Philipp
 */
public class DefaultAuthentication<T> implements Authentication {
    public final static Object SYSTEM_PRINCIPAL = "SYSTEM_PRINCIPAL";
    public final static Object LOCAL_ADMIN_PRINCIPAL = "LOCAL_ADMIN_PRINCIPAL";
    private final T principal;
    private final Long userId;
    private final Collection<? extends GrantedAuthority> authorities;

    public DefaultAuthentication(T principal, Long userId, String... roles) {
        this.principal = principal;
        this.userId = userId;
        this.authorities = AuthorityUtils.createAuthorityList(roles);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Long getCredentials() {
        return userId;
    }

    @Override
    public Object getDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return principal.toString();
    }

}
