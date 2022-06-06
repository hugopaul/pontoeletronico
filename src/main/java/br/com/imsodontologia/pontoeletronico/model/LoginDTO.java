package br.com.imsodontologia.pontoeletronico.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {

    private String usuario;
    private String password;

}