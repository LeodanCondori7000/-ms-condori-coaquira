package com.codigo.mscondoricoaquira.domain.ports.out;

import com.codigo.mscondoricoaquira.domain.aggregates.dto.PersonaDto;
import com.codigo.mscondoricoaquira.domain.aggregates.request.PersonaRequest;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceOut {
    PersonaDto crearPersonaOut(PersonaRequest personaRequest);
    Optional<PersonaDto> buscarxIdOut(Long id);
    List<PersonaDto> buscarTodosOut();
    PersonaDto actualizarOut(Long id, PersonaRequest personaRequest);
    PersonaDto eliminarOut(Long id);
}
