package com.dam2.Practica1.DTO.Pelicula;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaDTO {
    private Long id;
    private String titulo;
    private int duracion;
    private LocalDate fechaEstreno;
    private String sinopsis;
    private int valoracion;

    // CAMPOS NUEVOS
    private String posterUrl;
    private String backdropUrl;

    private Long directorId;
    private List<Long> actorIds;
    private List<Long> categoriaIds;
    private List<Long> idiomaIds;
    private List<Long> plataformaIds;
}