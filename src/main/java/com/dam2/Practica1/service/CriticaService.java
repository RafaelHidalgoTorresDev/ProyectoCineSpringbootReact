package com.dam2.Practica1.service;

import org.springframework.transaction.annotation.Transactional;

import com.dam2.Practica1.DTO.Critica.CriticaCreateUpdate;
import com.dam2.Practica1.DTO.Critica.CriticaDTO;
import com.dam2.Practica1.domain.Critica;
import com.dam2.Practica1.domain.Pelicula;
import com.dam2.Practica1.domain.Usuario;
import com.dam2.Practica1.repository.CriticaRepository;
import com.dam2.Practica1.repository.PeliculaRepository;
import com.dam2.Practica1.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CriticaService {

    private final CriticaRepository criticaRepository;
    private final PeliculaRepository peliculaRepository;
    private final UsuarioRepository usuarioRepository;

    // LISTAR TODAS
    public List<CriticaDTO> listar() {
        List<Critica> criticas = criticaRepository.findAll();
        List<CriticaDTO> dtos = new ArrayList<>();

        for (Critica c : criticas) {
            dtos.add(toDTO(c));
        }
        return dtos;
    }

    // BUSCAR POR ID
    public CriticaDTO buscarPorId(Long id) {
        Optional<Critica> opcional = criticaRepository.findById(id);
        if (opcional.isPresent()) {
            return toDTO(opcional.get());
        }
        return null;
    }

    // CREAR CRÍTICA
    public CriticaDTO crear(CriticaCreateUpdate dto) {
        Critica critica = new Critica();

        critica.setNota(dto.getNota());
        critica.setComentario(dto.getComentario());
        critica.setFecha(dto.getFecha());

        // Buscar la Película
        if (dto.getPeliculaId() != null) {
            Optional<Pelicula> peliOpt = peliculaRepository.findById(dto.getPeliculaId());
            if (peliOpt.isPresent()) {
                critica.setPelicula(peliOpt.get());
            } else {
                return null;
            }
        }

        // Buscar al Usuario
        if (dto.getUsuarioId() != null) {
            Optional<Usuario> userOpt = usuarioRepository.findById(dto.getUsuarioId());
            if (userOpt.isPresent()) {
                critica.setUsuario(userOpt.get());
            } else {
                return null;
            }
        }

        Critica guardada = criticaRepository.save(critica);
        return toDTO(guardada);
    }

    // 4. ACTUALIZAR CRÍTICA
    public CriticaDTO actualizar(Long id, CriticaCreateUpdate dto) {
        Optional<Critica> opcional = criticaRepository.findById(id);

        if (opcional.isPresent()) {
            Critica critica = opcional.get();

            critica.setNota(dto.getNota());
            critica.setComentario(dto.getComentario());
            critica.setFecha(dto.getFecha());

            if (dto.getPeliculaId() != null) {
                Optional<Pelicula> p = peliculaRepository.findById(dto.getPeliculaId());
                if (p.isPresent())
                    critica.setPelicula(p.get());
            }

            if (dto.getUsuarioId() != null) {
                Optional<Usuario> u = usuarioRepository.findById(dto.getUsuarioId());
                if (u.isPresent())
                    critica.setUsuario(u.get());
            }

            Critica guardada = criticaRepository.save(critica);
            return toDTO(guardada);
        }
        return null;
    }

    public boolean borrar(Long id) {
        if (criticaRepository.existsById(id)) {
            criticaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Método auxiliar para convertir a DTO
    private CriticaDTO toDTO(Critica critica) {
        CriticaDTO dto = new CriticaDTO();
        dto.setId(critica.getId());
        dto.setNota(critica.getNota());
        dto.setComentario(critica.getComentario());
        dto.setFecha(critica.getFecha());

        if (critica.getPelicula() != null) {
            dto.setPeliculaId(critica.getPelicula().getId());
        }

        if (critica.getUsuario() != null) {
            dto.setUsuarioId(critica.getUsuario().getId());
            dto.setUsuarioNombre(critica.getUsuario().getUsername());
        }

        return dto;
    }
}
