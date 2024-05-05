package com.codigo.mscondoricoaquira.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Entity
@Table(name = "persona")
@Getter
@Setter
public class PersonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 255)
    private String apellido;

    @Column(name = "tipoDocumento", nullable = false, length = 5)
    private String tipoDocumento;

    @Column(name = "numeroDocumento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;

    //@Column(name = "email", nullable = false, unique = true, length = 255)
    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "estado", nullable = false)
    private int estado;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresa;
}
