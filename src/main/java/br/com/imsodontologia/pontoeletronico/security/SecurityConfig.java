package br.com.imsodontologia.pontoeletronico.security;

import br.com.imsodontologia.pontoeletronico.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    };

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity.authorizeRequests()
                //.antMatchers("/colaborador", "/colaborador/**").hasRole("GERENTE")
                //.antMatchers(HttpMethod.PUT, "/lancamento", "/lancamento/**").hasRole("GERENTE")
                //.antMatchers(HttpMethod.POST, "/lancamento").hasRole("GERENTE")
                //.antMatchers(HttpMethod.GET, "/lancamento/colaborador/**").hasRole("GERENTE")
                //.antMatchers(HttpMethod.GET, "/lancamento/all", "/lancamento/**").hasRole("GERENTE")
                //.antMatchers("/**").hasRole("PROPRIETARIO")
                .anyRequest().authenticated();
                        //.anyRequest().permitAll();
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil));
        httpSecurity.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfiguration(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth ) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
