package com.dam2.Practica1.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "peliculas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private int duracion;

    private LocalDate fechaEstreno;

    @Column(length = 2000)
    private String sinopsis;

    private int valoracion;

    // --- NUEVOS CAMPOS (IMPORTANTE: El orden afecta al DataLoader) ---
    private String posterUrl;
    private String backdropUrl;
    // ----------------------------------------------------------------

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany
    @JoinTable(
            name = "pelicula_actor",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pelicula_categoria",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pelicula_idioma",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id")
    )
    private List<Idioma> idiomas = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pelicula_plataforma",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "plataforma_id")
    )
    private List<Plataforma> plataformas = new ArrayList<>();

    @OneToMany(mappedBy = "pelicula")
    private List<Critica> criticas = new ArrayList<>();

    @ManyToMany(mappedBy = "vistas")
    private List<Usuario> vistosPor = new ArrayList<>();
}