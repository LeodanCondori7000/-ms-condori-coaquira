package com.codigo.mscondoricoaquira.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Entity
@Table(name = "empresa_info")
@Getter
@Setter
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razonSocial", nullable = false)
    private String razonSocial;

    @Column(name = "tipoDocumento", nullable = false, length = 5)
    private String tipoDocumento;

    @Column(name = "numeroDocumento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;

    @Column(name = "estado", nullable = false)
    private int estado;

    @Column(name = "condicion", nullable = false, length = 50)
    private String condicion;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "distrito", length = 100)
    private String distrito;

    @Column(name = "provincia", length = 100)
    private String provincia;

    @Column(name = "departamento", length = 100)
    private String departamento;

    @Column(name = "EsAgenteRetencion", nullable = false)
    private boolean esAgenteRetencion;

    @Column(name = "usuaCrea", nullable = false, length = 255)
    private String usuarioCrea;

    @Column(name = "dateCreate", nullable = false)
    private Timestamp fechaCreacion;

    @Column(name = "usuaModif", length = 255)
    private String usuarioModifica;

    @Column(name = "dateModif")
    private Timestamp fechaModificacion;

    @Column(name = "usuaDelet", length = 255)
    private String usuarioBorra;

    @Column(name = "dateDelet")
    private Timestamp fechaBorrado;
}
