package com.dam2.Practica1.web;

import com.dam2.Practica1.DTO.Critica.CriticaCreateUpdate;
import com.dam2.Practica1.DTO.Critica.CriticaDTO;
import com.dam2.Practica1.service.CriticaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criticas")
@RequiredArgsConstructor
public class CriticaController {

    private final CriticaService criticaService;

    @GetMapping
    public ResponseEntity<List<CriticaDTO>> listar() {
        return ResponseEntity.ok(criticaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriticaDTO> buscarPorId(@PathVariable Long id) {
        CriticaDTO dto = criticaService.buscarPorId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CriticaDTO> crear(@Valid @RequestBody CriticaCreateUpdate dto) {
        CriticaDTO creada = criticaService.crear(dto);

        if (creada == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriticaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CriticaCreateUpdate dto) {
        CriticaDTO actualizada = criticaService.actualizar(id, dto);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        boolean borrado = criticaService.borrar(id);
        if (borrado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
