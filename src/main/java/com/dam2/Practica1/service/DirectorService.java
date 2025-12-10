package com.dam2.Practica1.service;

import org.springframework.transaction.annotation.Transactional;

import com.dam2.Practica1.DTO.Director.DirectorDTO;
import com.dam2.Practica1.domain.Director;
import com.dam2.Practica1.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DirectorService {

    private final DirectorRepository directorRepository;

    public List<DirectorDTO> listar() {
        List<Director> directores = directorRepository.findAll();
        List<DirectorDTO> dtos = new ArrayList<>();

        for (Director d : directores) {
            DirectorDTO dto = new DirectorDTO();
            dto.setId(d.getId());
            dto.setNombre(d.getNombre());
            dtos.add(dto);
        }
        return dtos;
    }

    public DirectorDTO buscarPorId(Long id) {
        Optional<Director> opcional = directorRepository.findById(id);
        if (opcional.isPresent()) {
            Director d = opcional.get();
            DirectorDTO dto = new DirectorDTO();
            dto.setId(d.getId());
            dto.setNombre(d.getNombre());
            return dto;
        }
        return null;
    }

    public DirectorDTO crear(DirectorDTO dto) {
        Director director = new Director();
        director.setNombre(dto.getNombre());

        Director guardado = directorRepository.save(director);

        dto.setId(guardado.getId());
        return dto;
    }

    public DirectorDTO actualizar(Long id, DirectorDTO dto) {
        Optional<Director> opcional = directorRepository.findById(id);
        if (opcional.isPresent()) {
            Director director = opcional.get();
            director.setNombre(dto.getNombre());

            Director guardado = directorRepository.save(director);
            dto.setId(guardado.getId());
            return dto;
        }
        return null;
    }

    public boolean borrar(Long id) {
        if (directorRepository.existsById(id)) {
            directorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
