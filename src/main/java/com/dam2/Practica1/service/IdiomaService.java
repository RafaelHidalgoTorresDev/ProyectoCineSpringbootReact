package com.dam2.Practica1.service;

import org.springframework.transaction.annotation.Transactional;

import com.dam2.Practica1.DTO.Idioma.IdiomaDTO;
import com.dam2.Practica1.domain.Idioma;
import com.dam2.Practica1.repository.IdiomaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class IdiomaService {

    private final IdiomaRepository idiomaRepository;

    public List<IdiomaDTO> listar() {
        List<Idioma> idiomas = idiomaRepository.findAll();
        List<IdiomaDTO> dtos = new ArrayList<>();

        for (Idioma i : idiomas) {
            dtos.add(toDTO(i));
        }
        return dtos;
    }

    public IdiomaDTO buscarPorId(Long id) {
        Optional<Idioma> opcional = idiomaRepository.findById(id);
        if (opcional.isPresent()) {
            return toDTO(opcional.get());
        }
        return null;
    }

    public IdiomaDTO crear(IdiomaDTO dto) {
        Idioma idioma = new Idioma();
        idioma.setNombre(dto.getNombre());

        Idioma guardado = idiomaRepository.save(idioma);
        return toDTO(guardado);
    }

    public IdiomaDTO actualizar(Long id, IdiomaDTO dto) {
        Optional<Idioma> opcional = idiomaRepository.findById(id);

        if (opcional.isPresent()) {
            Idioma idioma = opcional.get();
            idioma.setNombre(dto.getNombre());

            Idioma guardado = idiomaRepository.save(idioma);
            return toDTO(guardado);
        }
        return null;
    }

    public boolean borrar(Long id) {
        if (idiomaRepository.existsById(id)) {
            idiomaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private IdiomaDTO toDTO(Idioma i) {
        IdiomaDTO dto = new IdiomaDTO();
        dto.setId(i.getId());
        dto.setNombre(i.getNombre());
        return dto;
    }
}
