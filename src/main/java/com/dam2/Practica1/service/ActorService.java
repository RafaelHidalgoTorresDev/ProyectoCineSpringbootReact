package com.dam2.Practica1.service;

import org.springframework.transaction.annotation.Transactional;

import com.dam2.Practica1.DTO.Actor.ActorDTO;
import com.dam2.Practica1.domain.Actor;
import com.dam2.Practica1.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ActorService {

    private final ActorRepository actorRepository;

    public List<ActorDTO> listar() {
        List<Actor> actores = actorRepository.findAll();
        List<ActorDTO> dtos = new ArrayList<>();

        for (Actor a : actores) {
            ActorDTO dto = new ActorDTO();
            dto.setId(a.getId());
            dto.setNombre(a.getNombre());
            dtos.add(dto);
        }
        return dtos;
    }

    public ActorDTO buscarPorId(Long id) {
        Optional<Actor> opcional = actorRepository.findById(id);
        if (opcional.isPresent()) {
            Actor a = opcional.get();
            ActorDTO dto = new ActorDTO();
            dto.setId(a.getId());
            dto.setNombre(a.getNombre());
            return dto;
        }
        return null;
    }

    public ActorDTO crear(ActorDTO dto) {
        Actor actor = new Actor();
        actor.setNombre(dto.getNombre());

        Actor guardado = actorRepository.save(actor);

        dto.setId(guardado.getId());
        return dto;
    }

    public ActorDTO actualizar(Long id, ActorDTO dto) {
        Optional<Actor> opcional = actorRepository.findById(id);
        if (opcional.isPresent()) {
            Actor actor = opcional.get();
            actor.setNombre(dto.getNombre());

            Actor guardado = actorRepository.save(actor);
            dto.setId(guardado.getId());
            return dto;
        }
        return null;
    }

    public boolean borrar(Long id) {
        if (actorRepository.existsById(id)) {
            actorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
