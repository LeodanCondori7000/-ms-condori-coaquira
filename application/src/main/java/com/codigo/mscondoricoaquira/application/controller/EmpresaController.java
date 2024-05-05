package com.codigo.mscondoricoaquira.application.controller;

import com.codigo.mscondoricoaquira.domain.aggregates.dto.EmpresaDto;
import com.codigo.mscondoricoaquira.domain.aggregates.request.EmpresaRequest;
import com.codigo.mscondoricoaquira.domain.ports.in.EmpresaServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ms-condori-coaquira/v1/empresa")
@AllArgsConstructor
public class EmpresaController {
    private final EmpresaServiceIn empresaServiceIn;
    @PostMapping
    public ResponseEntity<EmpresaDto> crearEmpresa(@RequestBody EmpresaRequest empresaRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empresaServiceIn.crearEmpresaIn(empresaRequest));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> buscarxId(@PathVariable Long id) {

        Optional<EmpresaDto> empresaDto = empresaServiceIn.buscarxIdIn(id);

        if (empresaDto.isPresent()) {
            return ResponseEntity.ok(empresaDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/todos")
    public ResponseEntity<List<EmpresaDto>> buscarTodos() {
        List<EmpresaDto> listaDtos = empresaServiceIn.buscarTodosIn();
        return ResponseEntity.ok(listaDtos);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> actualizar(@PathVariable Long id, @RequestBody EmpresaRequest empresaRequest) {
        EmpresaDto updatedEmpresaDto = empresaServiceIn.actualizarIn(id, empresaRequest);

        if (updatedEmpresaDto != null) {
            return ResponseEntity.ok(updatedEmpresaDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<EmpresaDto> delete(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(empresaServiceIn.eliminarIn(id));
    }
}

