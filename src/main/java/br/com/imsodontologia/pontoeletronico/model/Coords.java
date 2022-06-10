package br.com.imsodontologia.pontoeletronico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coords {
    private String accuracy ;
    private String altitude;
    private String altitudeAccuracy;
    private String heading;
    private String latitude;
    private String longitude;
    private String speed;
}
