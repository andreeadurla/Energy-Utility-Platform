package ds.assignment.dtos.rest;

import ds.assignment.entities.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserDetailsImpl implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private UUID clientId;

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return UserDetailsImpl.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .authorities(authorities)
                            .clientId((user.getClient() != null) ? user.getClient().getId(): null)
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
        return username;
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
