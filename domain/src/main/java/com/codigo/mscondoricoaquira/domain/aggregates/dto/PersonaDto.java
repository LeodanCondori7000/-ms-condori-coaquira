package com.codigo.mscondoricoaquira.domain.aggregates.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PersonaDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private String email;
    private String telefono;
    private String direccion;
    private int estado;
    private String usuarioCrea;
    private Timestamp fechaCreacion;
    private String usuarioModifica;
    private Timestamp fechaModificacion;
    private String usuarioBorra;
    private Timestamp fechaBorrado;
    private EmpresaDto empresa;
}
