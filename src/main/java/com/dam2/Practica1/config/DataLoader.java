package com.dam2.Practica1.config;

import com.dam2.Practica1.domain.Director;
import com.dam2.Practica1.domain.Pelicula;
import com.dam2.Practica1.repository.ActorRepository;
import com.dam2.Practica1.repository.DirectorRepository;
import com.dam2.Practica1.repository.PeliculaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.ArrayList;

@Configuration
public class DataLoader {

        @Bean
        CommandLineRunner initData(ActorRepository actorRepo, DirectorRepository directorRepo,
                        PeliculaRepository peliculaRepo) {
                return args -> {
                        if (peliculaRepo.count() > 50)
                                return;

                        System.out.println(">>> REPARANDO RAFA'S CINEMA...");

                        Director nolan = createDirector(directorRepo, "Christopher Nolan");
                        Director tarantino = createDirector(directorRepo, "Quentin Tarantino");
                        Director spielberg = createDirector(directorRepo, "Steven Spielberg");
                        Director scorsese = createDirector(directorRepo, "Martin Scorsese");
                        Director disney = createDirector(directorRepo, "Disney/Pixar");
                        Director phillips = createDirector(directorRepo, "Todd Phillips");
                        Director stanton = createDirector(directorRepo, "Andrew Stanton");
                        Director lasseter = createDirector(directorRepo, "John Lasseter");

                        // ================= ADULTOS (URLs TMDB Oficiales) =================

                        createPelicula(peliculaRepo, "Oppenheimer", 180, "2023-07-21",
                                        "La historia del físico J. Robert Oppenheimer.", 9,
                                        "https://image.tmdb.org/t/p/original/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
                                        "https://i0.wp.com/cocalecas.net/wp-content/uploads/2025/06/oppenheimer-cillian.webp?fit=1024%2C576&ssl=1",
                                        nolan);

                        createPelicula(peliculaRepo, "Joker", 122, "2019-10-04",
                                        "La caída de Arthur Fleck hacia la locura.", 9,
                                        "https://image.tmdb.org/t/p/original/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
                                        "https://image.tmdb.org/t/p/original/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg",
                                        phillips);

                        createPelicula(peliculaRepo, "Interstellar", 169, "2014-11-07",
                                        "Un viaje a través de un agujero de gusano.", 10,
                                        "https://www.cinesur.com/data/fotos/interstellar-3213842.jpg",
                                        "https://image.tmdb.org/t/p/original/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg", nolan);

                        createPelicula(peliculaRepo, "Pulp Fiction", 154, "1994-10-14",
                                        "Historias de crimen en Los Ángeles.", 9,
                                        "https://image.tmdb.org/t/p/original/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
                                        "https://imagenes.hobbyconsolas.com/files/image_640_360/uploads/imagenes/2023/04/25/6902fa2f7f209.jpeg",
                                        tarantino);

                        createPelicula(peliculaRepo, "El Padrino", 175, "1972-03-14",
                                        "El patriarca de una dinastía del crimen.", 10,
                                        "https://image.tmdb.org/t/p/original/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
                                        "https://image.tmdb.org/t/p/original/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
                                        scorsese);

                        createPelicula(peliculaRepo, "El Caballero Oscuro", 152, "2008-07-18",
                                        "Batman se enfrenta al Joker.", 10,
                                        "https://image.tmdb.org/t/p/original/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                                        "https://image.tmdb.org/t/p/original/hkBaDkMWbLaf8B1lsWsKX7Ew3Xq.jpg", nolan);

                        createPelicula(peliculaRepo, "Matrix", 136, "1999-03-30", "Un hacker descubre la verdad.", 9,
                                        "https://belenpicadopsicologia.com/wp-content/uploads/2019/06/Cartel-de-Matrix-768x1019.jpg",
                                        "https://phantom-telva.uecdn.es/5515ab8595e5b16bc15d5a52ecb4810e/crop/0x5/1199x725/resize/828/f/jpg/assets/multimedia/imagenes/2019/08/30/15671514800698.jpg",
                                        nolan);

                        createPelicula(peliculaRepo, "El Club de la Lucha", 139, "1999-10-15",
                                        "La primera regla es no hablar del club.", 9,
                                        "https://image.tmdb.org/t/p/original/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
                                        "https://image.tmdb.org/t/p/original/hZkgoQYus5vegHoetLkCJzb17zJ.jpg", nolan);

                        createPelicula(peliculaRepo, "Inception", 148, "2010-07-16",
                                        "Un ladrón roba secretos a través de los sueños.", 9,
                                        "https://image.tmdb.org/t/p/original/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
                                        "https://image.tmdb.org/t/p/original/s3TBrRGB1iav7gFOCNx3H31MoES.jpg", nolan);

                        createPelicula(peliculaRepo, "Gladiator", 155, "2000-05-01",
                                        "Un general romano busca venganza.", 9,
                                        "https://i.pinimg.com/736x/b7/46/dc/b746dc7997fce4fef5efb5e1b6ce6e2f.jpg",
                                        "https://algomasquecine.com/wp-content/uploads/2023/11/gladiator-movie-review_jg9j.jpg",
                                        scorsese);

                        createPelicula(peliculaRepo, "El Lobo de Wall Street", 180, "2013-12-25",
                                        "La historia real de Jordan Belfort.", 9,
                                        "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhyNQ59FNfF9LQ_mbsLSOFdZO0N3W6jurYSTUY54f2zmy3MoBfJ8bMtL1P9bmRsRWek-EHe93pJvKez0no0ZSbrclU0tX7j8rLsmZucew9Fez2FrvZCO3XsLcfX5rnxfMzL4q2iaPU-Nb3H/s1600/wolf-of-wall-street-poster1.jpg",
                                        "https://alternativaseconomicas.coop/sites/default/files/shared/publicaciones/revistas/2014/revista12/fotos/ellobodewallstreet.jpg",
                                        scorsese);

                        createPelicula(peliculaRepo, "Seven", 127, "1995-09-22",
                                        "Dos detectives buscan a un asesino en serie.", 9,
                                        "https://es.web.img2.acsta.net/c_310_420/medias/nmedia/18/69/04/89/19757783.jpg",
                                        "https://s2.dmcdn.net/v/SoGfR1em-loLyKDkz/x1080", nolan);

                        createPelicula(peliculaRepo, "Top Gun: Maverick", 130, "2022-05-24",
                                        "Maverick entrena a una nueva generación.", 9,
                                        "https://i.ebayimg.com/images/g/zq8AAOSwCYtilvDM/s-l1200.jpg",
                                        "https://i.blogs.es/e43155/top_gun_maverick_ver6_xxlg/1366_2000.jpg",
                                        spielberg);

                        createPelicula(peliculaRepo, "Dune", 155, "2021-09-15",
                                        "Paul Atreides viaja al planeta peligroso.", 9,
                                        "https://static.posters.cz/image/1300/122815.jpg",
                                        "https://media.revistagq.com/photos/60faa6d464ee72f23c7a85f4/16:9/w_1360,h_765,c_limit/dune-trailer-estreno-espan%CC%83a.jpeg",
                                        nolan);

                        createPelicula(peliculaRepo, "The Batman", 176, "2022-03-01",
                                        "Batman investiga la corrupción en Gotham.", 8,
                                        "https://pics.filmaffinity.com/The_Batman-920683557-large.jpg",
                                        "https://image.tmdb.org/t/p/original/5P8SmMzSNYikXpxil6BYzJ16611.jpg", nolan);

                        createPelicula(peliculaRepo, "John Wick 4", 169, "2023-03-22", "John Wick contra la Alta Mesa.",
                                        9,
                                        "https://preview.redd.it/john-wick-ch-4-poster-v0-gn8tocf45sra1.jpg?auto=webp&s=f5d7b38283e4464371ae4abce81c0226200107e2",
                                        "https://media.revistagq.com/photos/6422ba5a62f41558efce8cab/master/pass/MCDJOWI_LG042.jpeg",
                                        tarantino);

                        createPelicula(peliculaRepo, "El Camino", 122, "2019-10-11",
                                        "Jesse Pinkman debe aceptar su pasado.", 8,
                                        "https://upload.wikimedia.org/wikipedia/pt/4/48/El_Camino_p%C3%B4ster.png",
                                        "https://estaticos-cdn.prensaiberica.es/clip/0e8913d4-195d-4c7a-9546-1264bdde87ec_16-9-discover-aspect-ratio_default_0.jpg",
                                        nolan);

                        createPelicula(peliculaRepo, "Avengers: Endgame", 181, "2019-04-24",
                                        "Los Vengadores reúnen fuerzas.", 9,
                                        "https://image.tmdb.org/t/p/original/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                                        "https://image.tmdb.org/t/p/original/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg", nolan);

                        createPelicula(peliculaRepo, "Forrest Gump", 142, "1994-06-23", "Corre, Forrest, corre.", 10,
                                        "https://pics.filmaffinity.com/Forrest_Gump-212765827-large.jpg",
                                        "https://estaticos-cdn.prensaiberica.es/clip/8e4ed9d6-ee2b-4224-93d4-a8c0b3d96074_16-9-aspect-ratio_default_0.jpg",
                                        spielberg);

                        // ================= NIÑOS (URLs TMDB Oficiales) =================

                        createPelicula(peliculaRepo, "Soul", 100, "2020-12-25",
                                        "Un músico viaja al mundo de las almas.", 8,
                                        "https://image.tmdb.org/t/p/original/hm58Jw4Lw8OIeECIq5qyPYhAeRJ.jpg",
                                        "https://image.tmdb.org/t/p/original/kf456ZqeC45XTvo6W9pW5clYKfQ.jpg", disney);

                        createPelicula(peliculaRepo, "Wall-E", 98, "2008-06-27", "Un robot en el espacio.", 9,
                                        "https://upload.wikimedia.org/wikipedia/en/4/4c/WALL-E_poster.jpg",
                                        "https://www.afundacion.org/images/fundacion_publicaciones/wall-e.jpg", disney);

                        createPelicula(peliculaRepo, "Up", 96, "2009-05-29", "Una casa que vuela con globos.", 9,
                                        "https://image.tmdb.org/t/p/original/vpbaStTMt8qqXAIVueipzG53Utx.jpg",
                                        "https://e01-elmundo.uecdn.es/elmundo/imagenes/2009/04/28/1240908932_0.jpg",
                                        disney);

                        createPelicula(peliculaRepo, "Toy Story", 81, "1995-11-22", "Juguetes vivientes.", 9,
                                        "https://image.tmdb.org/t/p/original/uXDfjJbdP4ijW5hWSBrPrlKpxab.jpg",
                                        "https://www.cinemanet.info/wp-content/uploads/2012/07/Toy-Story-CinemaNet-6-2.jpg",
                                        disney);

                        createPelicula(peliculaRepo, "Buscando a Nemo", 100, "2003-05-30", "Un pez busca a su hijo.", 8,
                                        "https://www.originalfilmart.com/cdn/shop/products/Finding_Nemo_2003_original_film_art_spo_5000x.jpg?v=1562544304",
                                        "https://assets.mubicdn.net/images/film/3382/image-w1280.jpg?1745489576",
                                        disney);

                        createPelicula(peliculaRepo, "El Rey León", 88, "1994-06-23", "Hakuna Matata.", 9,
                                        "https://cineycine.com/wp-content/uploads/2014/01/el-rey-leon-poster.jpg",
                                        "https://img.chilango.com/2018/11/el-rey-leon-version-live-action.jpg", disney);

                        createPelicula(peliculaRepo, "Coco", 105, "2017-10-27", "El día de los muertos.", 9,
                                        "https://es.web.img2.acsta.net/pictures/17/09/14/10/49/2019727.jpg",
                                        "https://media.gq.com.mx/photos/5f7cd8fc3515c53dd2a86373/master/w_2560%2Cc_limit/COCO.jpg",
                                        disney);

                        createPelicula(peliculaRepo, "Monstruos S.A.", 92, "2001-11-01", "Asustadores profesionales.",
                                        9,
                                        "https://image.tmdb.org/t/p/original/sgheSkDofREoM1c6t3yZqXm1gS2.jpg",
                                        "https://imagenes.hobbyconsolas.com/files/image_640_360/uploads/imagenes/2024/04/22/6903a5a4e1950.jpeg",
                                        disney);

                        createPelicula(peliculaRepo, "Los Increíbles", 115, "2004-11-05", "Superhéroes retirados.", 9,
                                        "https://image.tmdb.org/t/p/original/2LqaLgk4Z226KkgPJuiOQ58vdRM.jpg",
                                        "https://lumiere-a.akamaihd.net/v1/images/eu_incredibles-2_article_char1_m_06698432.jpeg?region=0,0,960,580",
                                        disney);

                        createPelicula(peliculaRepo, "Ratatouille", 111, "2007-06-22", "Una rata chef.", 9,
                                        "https://i0.wp.com/postresoriginales.com/wp-content/uploads/2013/05/Ratatouille-001.jpg?resize=500%2C711&ssl=1",
                                        "https://i0.wp.com/postresoriginales.com/wp-content/uploads/2013/05/Ratatouille-Pel%C3%ADcula-02.jpg?resize=800%2C533&ssl=1",
                                        disney);

                        createPelicula(peliculaRepo, "Cars", 117, "2006-06-09", "Coches de carreras.", 8,
                                        "https://fr.web.img5.acsta.net/c_310_420/pictures/17/08/01/16/24/309645.jpg",
                                        "https://images.justwatch.com/backdrop/175659241/s640/cars", disney);

                        createPelicula(peliculaRepo, "Shrek", 90, "2001-05-18", "El ogro y el asno.", 9,
                                        "https://image.tmdb.org/t/p/original/iB64vpL3dIObOtMZgX3RqdVdQDc.jpg",
                                        "https://preview.redd.it/i-think-the-shrek-5-trailer-is-just-a-joke-from-dreamworks-v0-255a5xa3egpe1.jpeg?auto=webp&s=d93ff68e739355467484ed829d029e6ed70c0670",
                                        disney);

                        createPelicula(peliculaRepo, "Frozen", 102, "2013-11-27", "Una reina con poderes de hielo.", 8,
                                        "https://e01-elmundo.uecdn.es/television/programacion-tv/img/v2/programas/9d/303773.png",
                                        "https://image.ondacero.es/clipping/cmsimages01/2019/12/07/8EB308C8-988C-4204-88C9-553D370A4606/69.jpg?crop=1200,675,x0,y0&width=1280&height=720&optimize=low&format=jpg",
                                        disney);

                        createPelicula(peliculaRepo, "Gru, mi villano favorito", 95, "2010-07-08",
                                        "Un villano adopta a tres niñas.", 8,
                                        "https://accioncine.es/wp-content/uploads/2010/10/gru-mi-villano-favorito.jpg",
                                        "https://www.ecartelera.com/images/noticias/69200/69251-h3.jpg", disney);

                        createPelicula(peliculaRepo, "Kung Fu Panda", 92, "2008-06-04",
                                        "Un panda torpe aprende artes marciales.", 9,
                                        "https://static.thcdn.com/images/small/original/productimg/960/960/9995501-6766549122892882.jpg",
                                        "https://media.vandal.net/i/1280x720/10-2023/21/2023102115203788_2.jpg",
                                        disney);

                        createPelicula(peliculaRepo, "Spider-Man: Into the Spider-Verse", 117, "2018-12-12",
                                        "Varios Spider-Man se unen.", 10,
                                        "https://image.tmdb.org/t/p/original/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg",
                                        "https://media.gq.com.mx/photos/5dc1b1e59c244300080d17ac/16:9/w_1280,c_limit/spider.jpg",
                                        disney);

                        createPelicula(peliculaRepo, "Zootrópolis", 108, "2016-02-11",
                                        "Una coneja policía en una gran ciudad.", 9,
                                        "https://upload.wikimedia.org/wikipedia/ca/7/75/Zootr%C3%B2polis.jpg",
                                        "https://mimamatieneunblog.com/wp-content/uploads/2016/01/zootropolis-judy-hopps.jpg",
                                        disney);

                        createPelicula(peliculaRepo, "Encanto", 102, "2021-11-24", "Una familia mágica en Colombia.", 9,
                                        "https://es.web.img2.acsta.net/pictures/21/09/29/16/57/5761354.jpg",
                                        "https://media-cldnry.s-nbcnews.com/image/upload/t_nbcnews-fp-1024-512,f_auto,q_auto:best/rockcms/2021-11/Encanto-Disney-707310.jpg",
                                        disney);

                        createPelicula(peliculaRepo, "Super Mario Bros", 92, "2023-04-05",
                                        "Mario viaja al Reino Champiñón.", 9,
                                        "https://m.media-amazon.com/images/M/MV5BMGQ3YjMwZDctZTkwNi00NmZjLWEyYmItZGFiYTYyMDUzZjFjXkEyXkFqcGc@._V1_.jpg",
                                        "https://image.tmdb.org/t/p/original/9n2tJBplPbgR2ca05hS5CKXwP2c.jpg", disney);

                        createPelicula(peliculaRepo, "Del Revés", 95, "2015-06-19",
                                        "Las emociones de una niña cobran vida.", 9,
                                        "https://pics.filmaffinity.com/Del_revaes_Inside_Out-161470323-large.jpg",
                                        "https://i.blogs.es/90edc9/inside_out_cartel/650_1200.jpg", disney);

                        System.out.println(">>> CARGA COMPLETADA ✔");
                };
        }

        private Director createDirector(DirectorRepository repo, String nombre) {
                Director d = repo.findByNombre(nombre);
                if (d == null)
                        d = repo.save(new Director(null, nombre, new ArrayList<>()));
                return d;
        }

        private void createPelicula(PeliculaRepository repo, String titulo, int duracion, String fecha, String sinopsis,
                        int valoracion, String poster, String backdrop, Director director) {
                if (repo.findByTitulo(titulo) == null) {
                        Pelicula p = new Pelicula(null, titulo, duracion, LocalDate.parse(fecha), sinopsis, valoracion,
                                        poster, backdrop, director, new ArrayList<>(), new ArrayList<>(),
                                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                        repo.save(p);
                }
        }
}