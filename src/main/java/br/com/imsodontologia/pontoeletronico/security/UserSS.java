package br.com.imsodontologia.pontoeletronico.security;

import br.com.imsodontologia.pontoeletronico.model.Perfil;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@Setter
public class UserSS implements UserDetails {
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public UserSS() {
    }

    public UserSS(UUID id, String username, String password, Set<Perfil> perfis) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority("ROLE_".concat(x.getNmPerfil()))).collect(Collectors.toList());
    }

    public UUID getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
