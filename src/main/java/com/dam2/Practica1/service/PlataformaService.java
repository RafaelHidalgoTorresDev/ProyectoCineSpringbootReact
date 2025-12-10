package com.dam2.Practica1.service;

import org.springframework.transaction.annotation.Transactional;

import com.dam2.Practica1.DTO.Plataforma.PlataformaDTO;
import com.dam2.Practica1.domain.Plataforma;
import com.dam2.Practica1.repository.PlataformaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlataformaService {

    private final PlataformaRepository plataformaRepository;

    public List<PlataformaDTO> listar() {
        List<Plataforma> plataformas = plataformaRepository.findAll();
        List<PlataformaDTO> dtos = new ArrayList<>();

        for (Plataforma p : plataformas) {
            dtos.add(toDTO(p));
        }
        return dtos;
    }

    public PlataformaDTO buscarPorId(Long id) {
        Optional<Plataforma> opcional = plataformaRepository.findById(id);
        if (opcional.isPresent()) {
            return toDTO(opcional.get());
        }
        return null;
    }

    public PlataformaDTO crear(PlataformaDTO dto) {
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre(dto.getNombre());

        Plataforma guardada = plataformaRepository.save(plataforma);
        return toDTO(guardada);
    }

    public PlataformaDTO actualizar(Long id, PlataformaDTO dto) {
        Optional<Plataforma> opcional = plataformaRepository.findById(id);

        if (opcional.isPresent()) {
            Plataforma plataforma = opcional.get();
            plataforma.setNombre(dto.getNombre());

            Plataforma guardada = plataformaRepository.save(plataforma);
            return toDTO(guardada);
        }
        return null;
    }

    public boolean borrar(Long id) {
        if (plataformaRepository.existsById(id)) {
            plataformaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PlataformaDTO toDTO(Plataforma p) {
        PlataformaDTO dto = new PlataformaDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        return dto;
    }
}
