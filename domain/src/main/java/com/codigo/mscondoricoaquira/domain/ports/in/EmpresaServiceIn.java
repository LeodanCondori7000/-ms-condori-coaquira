package com.codigo.mscondoricoaquira.domain.ports.in;

import com.codigo.mscondoricoaquira.domain.aggregates.dto.EmpresaDto;
import com.codigo.mscondoricoaquira.domain.aggregates.request.EmpresaRequest;

import java.util.List;
import java.util.Optional;
public interface EmpresaServiceIn {
    EmpresaDto crearEmpresaIn(EmpresaRequest empresaRequest);
    Optional<EmpresaDto> buscarxIdIn(Long id);
    List<EmpresaDto> buscarTodosIn();
    EmpresaDto actualizarIn(Long id, EmpresaRequest empresaRequest);
    EmpresaDto eliminarIn(Long id);
}
