package br.com.imsodontologia.pontoeletronico.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class RequestLancamento {

    private String latitude;
    private String longitude;
    private Timestamp timestamp;

    public RequestLancamento() {
    }

    public RequestLancamento(String latitude, String longitude, String timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = Timestamp.valueOf(timestamp);
    }
}
