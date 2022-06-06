package br.com.imsodontologia.pontoeletronico.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class GeolocationPosition {
    private Coords coords;
    private Timestamp timestamp;
}
