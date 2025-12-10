package com.dam2.Practica1.service;

import org.springframework.transaction.annotation.Transactional;

import com.dam2.Practica1.DTO.Usuario.UsuarioCreateUpdate;
import com.dam2.Practica1.DTO.Usuario.UsuarioDTO;
import com.dam2.Practica1.domain.Usuario;
import com.dam2.Practica1.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario u : usuarios) {
            dtos.add(toDTO(u));
        }
        return dtos;
    }

    public UsuarioDTO buscarPorId(Long id) {
        Optional<Usuario> opcional = usuarioRepository.findById(id);
        if (opcional.isPresent()) {
            return toDTO(opcional.get());
        }
        return null;
    }

    public UsuarioDTO crear(UsuarioCreateUpdate dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuario.setAvatar(dto.getAvatar());

        Usuario guardado = usuarioRepository.save(usuario);
        return toDTO(guardado);
    }

    public UsuarioDTO actualizar(Long id, UsuarioCreateUpdate dto) {
        Optional<Usuario> opcional = usuarioRepository.findById(id);
        if (opcional.isPresent()) {
            Usuario usuario = opcional.get();
            usuario.setUsername(dto.getUsername());
            usuario.setEmail(dto.getEmail());
            usuario.setPassword(dto.getPassword());
            usuario.setRol(dto.getRol());
            usuario.setAvatar(dto.getAvatar());

            Usuario guardado = usuarioRepository.save(usuario);
            return toDTO(guardado);
        }
        return null;
    }

    public boolean borrar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        dto.setAvatar(usuario.getAvatar());
        return dto;
    }
}