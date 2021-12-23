package ru.itis.threedportalserver.security.jwt;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.threedportalserver.models.PortalUser;

import java.util.Collection;
import java.util.Collections;

@Builder
@Data
public class UserDetailsImpl implements UserDetails {

    private PortalUser portalUser;

    public UserDetailsImpl(PortalUser portalUser) {
        this.portalUser = portalUser;
    }

    public PortalUser getPortalUser() {
        return portalUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername(){
        return portalUser.getEmail();
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
}