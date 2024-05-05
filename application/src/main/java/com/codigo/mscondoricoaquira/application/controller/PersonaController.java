package com.codigo.mscondoricoaquira.application.controller;

import com.codigo.mscondoricoaquira.domain.aggregates.dto.EmpresaDto;
import com.codigo.mscondoricoaquira.domain.aggregates.dto.PersonaDto;
import com.codigo.mscondoricoaquira.domain.aggregates.request.EmpresaRequest;
import com.codigo.mscondoricoaquira.domain.aggregates.request.PersonaRequest;
import com.codigo.mscondoricoaquira.domain.ports.in.PersonaServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ms-condori-coaquira/v1/persona")
@AllArgsConstructor
public class PersonaController {
    private final PersonaServiceIn personaServiceIn;
    @PostMapping
    public ResponseEntity<PersonaDto> crearPersona(@RequestBody PersonaRequest requestPersona){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.crearPersonaIn(requestPersona));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PersonaDto> buscarxId(@PathVariable Long id) {
        Optional<PersonaDto> personaDto = personaServiceIn.buscarxIdIn(id);

        if (personaDto.isPresent()) {
            return ResponseEntity.ok(personaDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/todos")
    public ResponseEntity<List<PersonaDto>> buscarTodos(){
        List<PersonaDto> listaDtos = personaServiceIn.buscarTodosIn();
        return ResponseEntity.ok(listaDtos);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PersonaDto> actualizar(@PathVariable Long id, @RequestBody PersonaRequest personaRequest) {
        PersonaDto updatedPersonaaDto = personaServiceIn.actualizarIn(id, personaRequest);

        if (updatedPersonaaDto != null) {
            return ResponseEntity.ok(updatedPersonaaDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PersonaDto> delete(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.eliminarIn(id));
    }
}
