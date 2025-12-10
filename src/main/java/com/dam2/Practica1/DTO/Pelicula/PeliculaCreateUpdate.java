package com.dam2.Practica1.DTO.Pelicula;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaCreateUpdate {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede tener más de 255 caracteres")
    private String titulo;

    @Min(value = 1, message = "La duración debe ser de al menos 1 minuto")
    @Max(value = 600, message = "La duración no puede ser mayor a 600 minutos")
    private int duracion;

    @NotNull(message = "La fecha de estreno es obligatoria")
    private LocalDate fechaEstreno;

    @Size(max = 2000, message = "La sinopsis no puede superar los 2000 caracteres")
    private String sinopsis;

    @Min(value = 0, message = "La valoración mínima es 0")
    @Max(value = 10, message = "La valoración máxima es 10")
    private int valoracion;

    // Opcional: permitir meter URLs al crear manualmente
    private String posterUrl;
    private String backdropUrl;

    private Long directorId;
    private List<Long> actorIds;
    private List<Long> categoriaIds;
    private List<Long> idiomaIds;
    private List<Long> plataformaIds;
}