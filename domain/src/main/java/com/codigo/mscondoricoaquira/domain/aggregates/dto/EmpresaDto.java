package com.codigo.mscondoricoaquira.domain.aggregates.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class EmpresaDto {
    private Long id;
    private String razonSocial;
    private String tipoDocumento;
    private String numeroDocumento;
    private int estado;
    private String condicion;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
    private boolean esAgenteRetencion;
    private String usuarioCrea;
    private Timestamp fechaCreacion;
    private String usuarioModifica;
    private Timestamp fechaModificacion;
    private String usuarioBorra;
    private Timestamp fechaBorrado;
}
