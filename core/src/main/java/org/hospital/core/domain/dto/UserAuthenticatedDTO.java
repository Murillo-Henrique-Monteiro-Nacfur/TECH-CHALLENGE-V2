package org.hospital.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticatedDTO extends Throwable implements UserDetails {

    public UserAuthenticatedDTO(Long id, String username, String password, List<String> authorities) {
        super();
        this.id = id;
        this.password = password;
        this.username = username;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.authorities = authorities;
    }

    private Long id;
    private String username;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    private List<String> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of("ADMIN", "DOCTOR", "NURSE")
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
                .toList();
        //authorities.stream().map(role -> (GrantedAuthority) () -> "ROLE_" + role).toList();

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
