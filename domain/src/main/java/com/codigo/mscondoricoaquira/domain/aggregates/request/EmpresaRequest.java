package com.codigo.mscondoricoaquira.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EmpresaRequest {
    private String numeroDocumento;
    private String direccion;
    private String distrito;
    private  String provincia;
    private String departamento;
}
