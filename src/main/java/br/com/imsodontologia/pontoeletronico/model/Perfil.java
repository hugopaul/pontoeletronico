package br.com.imsodontologia.pontoeletronico.model;

import lombok.*;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@Table(schema = "pontoeletronico", name = "tb_perfil",uniqueConstraints = @UniqueConstraint(columnNames = {"cd_perfil"}))
@NoArgsConstructor
public class Perfil {

    @Id
    @Column(name = "cd_perfil")
    private UUID cdPerfil;

    @Column(name = "nm_perfil")
    private String nmPerfil;


}
