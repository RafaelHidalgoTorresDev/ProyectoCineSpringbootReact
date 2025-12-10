package com.dam2.Practica1.web;

import com.dam2.Practica1.DTO.Actor.ActorDTO;
import com.dam2.Practica1.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actores")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService service;

    @GetMapping
    public ResponseEntity<List<ActorDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> buscarPorId(@PathVariable Long id) {
        ActorDTO dto = service.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ActorDTO> crear(@RequestBody ActorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorDTO> actualizar(@PathVariable Long id, @RequestBody ActorDTO dto) {
        ActorDTO actualizado = service.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (service.borrar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}