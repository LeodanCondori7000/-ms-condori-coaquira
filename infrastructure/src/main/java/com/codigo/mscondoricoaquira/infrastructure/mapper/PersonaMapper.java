package com.codigo.mscondoricoaquira.infrastructure.mapper;

import com.codigo.mscondoricoaquira.domain.aggregates.dto.PersonaDto;
import com.codigo.mscondoricoaquira.infrastructure.entity.PersonaEntity;

public class PersonaMapper {
    public static PersonaDto fromEntity(PersonaEntity personaEntity) {
        PersonaDto dto = new PersonaDto();
        dto.setId(personaEntity.getId());
        dto.setNombre(personaEntity.getNombre());
        dto.setApellido(personaEntity.getApellido());
        dto.setTipoDocumento(personaEntity.getTipoDocumento());
        dto.setNumeroDocumento(personaEntity.getNumeroDocumento());
        dto.setEmail(personaEntity.getEmail());
        dto.setTelefono(personaEntity.getTelefono());
        dto.setDireccion(personaEntity.getDireccion());
        dto.setEstado(personaEntity.getEstado());
        dto.setUsuarioCrea(personaEntity.getUsuarioCrea());
        dto.setFechaCreacion(personaEntity.getFechaCreacion());
        dto.setUsuarioModifica(personaEntity.getUsuarioModifica());
        dto.setFechaModificacion(personaEntity.getFechaModificacion());
        dto.setUsuarioBorra(personaEntity.getUsuarioBorra());
        dto.setFechaBorrado(personaEntity.getFechaBorrado());
        // Mapping for the associated EmpresaEntity
        if (personaEntity.getEmpresa() != null) {
            dto.setEmpresa(EmpresaMapper.fromEntity(personaEntity.getEmpresa()));
        }
        return dto;
    }
}
