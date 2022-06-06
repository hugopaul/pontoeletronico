package br.com.imsodontologia.pontoeletronico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "pontoeletronico", name = "tb_lancamento",uniqueConstraints = @UniqueConstraint(columnNames = {"cd_lancamento"}))
public class Lancamento {
    @Id
    @Column(name = "cd_lancamento", nullable = false)
    @GeneratedValue(generator = UUIDGenerator.UUID_GEN_STRATEGY)
    private UUID cdLancamento;

    @ManyToOne
    @JoinColumn(name = "cd_colaborador")
    private Colaborador cdColaborador;

    @Column(name = "dh_marcacao")
    private Timestamp dhMarcacao;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "valid")
    private Boolean valid;

}
