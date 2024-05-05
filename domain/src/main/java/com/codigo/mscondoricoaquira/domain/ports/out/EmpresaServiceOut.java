package com.codigo.mscondoricoaquira.domain.ports.out;

import com.codigo.mscondoricoaquira.domain.aggregates.dto.EmpresaDto;
import com.codigo.mscondoricoaquira.domain.aggregates.request.EmpresaRequest;

import java.util.List;
import java.util.Optional;
public interface EmpresaServiceOut {
    EmpresaDto crearEmpresaOut(EmpresaRequest empresaRequest);
    Optional<EmpresaDto> buscarxIdOut(Long id);
    List<EmpresaDto> buscarTodosOut();
    EmpresaDto actualizarOut(Long id, EmpresaRequest empresaRequest);
    EmpresaDto eliminarOut(Long id);
}
