package com.dam2.Practica1.DTO.Critica;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriticaCreateUpdate {

    @NotNull(message = "La nota es obligatoria")
    @DecimalMin(value = "0.0", message = "La nota mínima es 0")
    @DecimalMax(value = "10.0", message = "La nota máxima es 10")
    private Double nota;

    @NotBlank(message = "El comentario es obligatorio")
    private String comentario;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "Falta el ID de la película")
    private Long peliculaId;

    @NotNull(message = "Falta el ID del usuario")
    private Long usuarioId;
}