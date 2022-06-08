package br.com.imsodontologia.pontoeletronico.model;

import lombok.Data;

@Data
public class PassMatches {
    private String encryptedPass;
    private String passToMatch;
}
