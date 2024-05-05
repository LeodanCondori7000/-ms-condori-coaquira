package com.codigo.mscondoricoaquira.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PersonaRequest {
    private String numeroDocumento;
    private Long empresa_id;
    private String email;
    private String telefono;
    private String direccion;
}
