package com.dam2.Practica1.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plataforma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plataforma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String url;

    @ManyToMany(mappedBy = "plataformas")
    private List<Pelicula> peliculas = new ArrayList<>();
}
