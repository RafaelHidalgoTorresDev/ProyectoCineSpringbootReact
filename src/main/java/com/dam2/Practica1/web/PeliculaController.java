package com.dam2.Practica1.web;

import com.dam2.Practica1.DTO.Pelicula.PeliculaCreateUpdate;
import com.dam2.Practica1.DTO.Pelicula.PeliculaDTO;
import com.dam2.Practica1.domain.Pelicula;
import com.dam2.Practica1.service.PeliculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/peliculas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PeliculaController {

    private final PeliculaService service;

    @GetMapping // GET /api/peliculas
    public ResponseEntity<List<PeliculaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}") // GET /api/peliculas/{id}
    public ResponseEntity<PeliculaDTO> buscarPorId(@PathVariable Long id) {
        PeliculaDTO dto = service.buscarPorId(id);

        if (dto == null) {
            return ResponseEntity.notFound().build(); // Devuelve error 404 si no existe
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping // POST /api/peliculas
    public ResponseEntity<PeliculaDTO> crear(@Valid @RequestBody PeliculaCreateUpdate dto) {
        PeliculaDTO creada = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada); // Devuelve código 201
    }

    @PutMapping("/{id}") // PUT /api/peliculas/{id}
    public ResponseEntity<PeliculaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PeliculaCreateUpdate dto) {
        PeliculaDTO actualizada = service.actualizar(id, dto);

        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}") // DELETE /api/peliculas/{id}
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        boolean borrado = service.borrar(id);

        if (borrado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 No existe
        }
    }

    @GetMapping("/peliculas_mejores") // GET /api/peliculas/peliculas_mejores
    public List<Pelicula> mejores_peliculas() {
        return service.mejores_peliculas(5);
    }

    @GetMapping("/procesar") // GET /api/peliculas/procesar
    public String procesarPeliculas() {
        long inicio = System.currentTimeMillis();

        service.tareaLenta("Interstellar");
        service.tareaLenta("The Dark Knight");
        service.tareaLenta("Soul");

        long fin = System.currentTimeMillis();
        return "Tiempo total: " + (fin - inicio) + " ms";
    }

    @GetMapping("/procesarAsync") // GET /api/peliculas/procesarAsync
    public String procesarAsync() {
        long inicio = System.currentTimeMillis();

        var t1 = service.tareaLenta2("🍿 Interstellar");
        var t2 = service.tareaLenta2("🦇 The Dark Knight");
        var t3 = service.tareaLenta2("🎵 Soul");
        var t4 = service.tareaLenta2("🎵 Soul");
        var t5 = service.tareaLenta2("🎵 Soul");
        var t6 = service.tareaLenta2("🎵 Soul");

        CompletableFuture.allOf(t1, t2, t3, t4, t5, t6).join();

        long fin = System.currentTimeMillis();
        return "Tiempo total (asíncrono): " + (fin - inicio) + " ms";
    }

    @GetMapping("/reproducir") // GET /api/peliculas/reproducir
    public String reproducirAsync() {
        long inicio = System.currentTimeMillis();

        var t1 = service.reproducir("🍿 Interstellar");
        var t2 = service.reproducir("🦇 The Dark Knight");
        var t3 = service.reproducir("🎵 Soul");

        CompletableFuture.allOf(t1, t2, t3).join();

        long fin = System.currentTimeMillis();
        return "Tiempo total (asíncrono): " + (fin - inicio) + " ms";
    }

    @GetMapping("/importar")
    public String importarPeliculas(@RequestParam String carpeta) {
        long inicio = System.currentTimeMillis();

        try {
            service.importarCarpeta(carpeta);
        } catch (Exception error) {
            return "No se importaron las peliculas: " + error.getMessage();
        }

        long fin = System.currentTimeMillis();
        long total = fin - inicio;

        return "Películas importadas en " + (total / 1000) + " segundos";
    }

    @GetMapping("/oscar")
    public List<String> votarOscars() {

        List<String> peliculas = Arrays.asList("Interstellar", "The Dark Knight", "Soul");

        PeliculaService.reiniciarVotos();

        int jurados = 10;

        List<CompletableFuture<Void>> listaDeVotos = new ArrayList<>();

        for (int i = 1; i <= jurados; i++) {
            final int juradoId = i;
            for (String titulo : peliculas) {
                CompletableFuture<Void> futuro = CompletableFuture.runAsync(new Runnable() {
                    @Override
                    public void run() {
                        PeliculaService.votar(titulo, juradoId);
                    }
                });
                listaDeVotos.add(futuro);
            }
        }

        CompletableFuture.allOf(listaDeVotos.toArray(new CompletableFuture[0])).join();

        return PeliculaService.ranking();
    }
}