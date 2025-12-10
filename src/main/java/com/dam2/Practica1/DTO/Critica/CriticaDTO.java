package com.dam2.Practica1.DTO.Critica;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriticaDTO {
    private Long id;
    private LocalDate fecha;
    private Double nota;
    private String comentario;
    private Long usuarioId;
    private String usuarioNombre;
    private Long peliculaId;
}