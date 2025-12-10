package com.dam2.Practica1.web;

import com.dam2.Practica1.DTO.Director.DirectorDTO;
import com.dam2.Practica1.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directores")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService service;

    @GetMapping
    public ResponseEntity<List<DirectorDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDTO> buscarPorId(@PathVariable Long id) {
        DirectorDTO dto = service.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<DirectorDTO> crear(@RequestBody DirectorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorDTO> actualizar(@PathVariable Long id, @RequestBody DirectorDTO dto) {
        DirectorDTO actualizado = service.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (service.borrar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
