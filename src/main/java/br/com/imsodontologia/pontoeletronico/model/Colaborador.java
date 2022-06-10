package br.com.imsodontologia.pontoeletronico.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.id.UUIDGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@Table(schema = "pontoeletronico", name = "tb_colaborador",uniqueConstraints = @UniqueConstraint(columnNames = {"cd_colaborador", "username"}))
@NoArgsConstructor
public class Colaborador{

    @Id
    @Column(name = "cd_colaborador")
    @GeneratedValue(generator = UUIDGenerator.UUID_GEN_STRATEGY)
    private UUID cdColaborador;

    @Column(name = "nm_colaborador")
    @NotEmpty
    private String nmColaborador;

    @Column(name = "username", unique=true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "telefone")
    private String telefone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tr_colaborador_perfil",
            schema = "pontoeletronico")
    private Set<Perfil> perfis;

    @JsonIgnore
    private boolean desativado;


}
