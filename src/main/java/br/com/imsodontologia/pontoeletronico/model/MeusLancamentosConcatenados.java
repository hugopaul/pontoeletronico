package br.com.imsodontologia.pontoeletronico.model;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;


@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeusLancamentosConcatenados implements Serializable {

    private String dia;

    private String lancamentos;

}
