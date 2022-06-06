package br.com.imsodontologia.pontoeletronico.service.impl;

import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.repository.ColaboradorRepository;
import br.com.imsodontologia.pontoeletronico.security.UserSS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Colaborador> colaborador = colaboradorRepository.findByUsername(username);

        if (colaborador.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        return new UserSS(colaborador.get().getCdColaborador(), colaborador.get().getUsername(),colaborador.get().getPassword(),colaborador.get().getPerfis());
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String username){

        Optional<Colaborador> colaborador = colaboradorRepository.findByUsername(username);

        if (colaborador.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        Collection<? extends GrantedAuthority> authorities = colaborador.get().getPerfis()
                                    .stream()
                                    .map(x -> new SimpleGrantedAuthority("ROLE_".concat(x.getNmPerfil())))
                                    .collect(Collectors.toList());
        log.info("SAÍDA DO GET AUTHORITIES ---> " + authorities);
        return authorities;
    }

}
