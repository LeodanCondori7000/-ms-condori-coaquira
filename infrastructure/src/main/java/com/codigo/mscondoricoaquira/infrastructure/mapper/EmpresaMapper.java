package com.codigo.mscondoricoaquira.infrastructure.mapper;

import com.codigo.mscondoricoaquira.domain.aggregates.dto.EmpresaDto;
import com.codigo.mscondoricoaquira.infrastructure.entity.EmpresaEntity;

public class EmpresaMapper {
    public static EmpresaDto fromEntity(EmpresaEntity empresaEntity){
        EmpresaDto dto = new EmpresaDto();
        dto.setId(empresaEntity.getId());
        dto.setRazonSocial(empresaEntity.getRazonSocial());
        dto.setTipoDocumento(empresaEntity.getTipoDocumento());
        dto.setNumeroDocumento(empresaEntity.getNumeroDocumento());
        dto.setEstado(empresaEntity.getEstado());
        dto.setCondicion(empresaEntity.getCondicion());
        dto.setDireccion(empresaEntity.getDireccion());
        dto.setDistrito(empresaEntity.getDistrito());
        dto.setProvincia(empresaEntity.getProvincia());
        dto.setDepartamento(empresaEntity.getDepartamento());
        dto.setEsAgenteRetencion(empresaEntity.isEsAgenteRetencion());
        dto.setUsuarioCrea(empresaEntity.getUsuarioCrea());
        dto.setFechaCreacion(empresaEntity.getFechaCreacion());
        dto.setUsuarioModifica(empresaEntity.getUsuarioModifica());
        dto.setFechaModificacion(empresaEntity.getFechaModificacion());
        dto.setUsuarioBorra(empresaEntity.getUsuarioBorra());
        dto.setFechaBorrado(empresaEntity.getFechaBorrado());
        return dto;
    }
}
