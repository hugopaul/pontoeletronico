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

    private GeolocationCoordinates geolocationCoordinates;
}
