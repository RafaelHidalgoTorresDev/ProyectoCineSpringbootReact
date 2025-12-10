package com.dam2.Practica1.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String rol;
    private String avatar;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Critica> criticas = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "usuario_pelicula_vista", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "pelicula_id"))
    private List<Pelicula> vistas = new ArrayList<>();

    public void addVista(Pelicula p) {
        if (!vistas.contains(p)) {
            vistas.add(p);
            p.getVistosPor().add(this);
        }
    }
}