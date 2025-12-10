package com.dam2.Practica1.service;

import org.springframework.transaction.annotation.Transactional;

import com.dam2.Practica1.DTO.Categoria.CategoriaDTO;
import com.dam2.Practica1.domain.Categoria;
import com.dam2.Practica1.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaService {

    private final CategoriaRepository repository;

    public List<CategoriaDTO> listar() {
        List<Categoria> lista = repository.findAll();
        List<CategoriaDTO> dtos = new ArrayList<>();
        for (Categoria c : lista) {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setId(c.getId());
            dto.setNombre(c.getNombre());
            dtos.add(dto);
        }
        return dtos;
    }

    public CategoriaDTO buscarPorId(Long id) {
        Optional<Categoria> opcional = repository.findById(id);
        if (opcional.isPresent()) {
            Categoria c = opcional.get();
            CategoriaDTO dto = new CategoriaDTO();
            dto.setId(c.getId());
            dto.setNombre(c.getNombre());
            return dto;
        }
        return null;
    }

    public CategoriaDTO crear(CategoriaDTO dto) {
        Categoria entidad = new Categoria();
        entidad.setNombre(dto.getNombre());

        Categoria guardada = repository.save(entidad);

        dto.setId(guardada.getId());
        return dto;
    }

    public CategoriaDTO actualizar(Long id, CategoriaDTO dto) {
        Optional<Categoria> opcional = repository.findById(id);
        if (opcional.isPresent()) {
            Categoria entidad = opcional.get();
            entidad.setNombre(dto.getNombre());

            Categoria guardada = repository.save(entidad);
            dto.setId(guardada.getId());
            return dto;
        }
        return null;
    }

    public boolean borrar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
