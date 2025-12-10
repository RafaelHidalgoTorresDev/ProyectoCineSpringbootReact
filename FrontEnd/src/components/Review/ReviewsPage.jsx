import React, { useState, useEffect } from 'react';
import { Star, MessageSquare } from 'lucide-react';

import { useAuth } from '../../context/AuthContext';

const ReviewsPage = () => {
    const { user } = useAuth();
    const [reviews, setReviews] = useState([]);
    const [movies, setMovies] = useState({});

    const KID_TAGS = ["Animated", "Family", "Cartoon", "Disney", "Pixar", "Dreamworks", "Soul", "Wall-E", "Up", "Toy Story", "Nemo", "Rey León", "Coco", "Monstruos", "Increíbles", "Ratatouille", "Cars", "Shrek", "Frozen", "Gru", "Panda", "Spider-Man", "Mario", "Encanto", "Del Revés"];

    useEffect(() => {
        fetchData();
    }, [user]);

    const fetchData = async () => {
        try {
            // Fetch Reviews
            const resReviews = await fetch("http://localhost:8081/api/criticas");
            const dataReviews = await resReviews.json();

            // Fetch Movies to map ID -> Title/Poster
            const resMovies = await fetch("http://localhost:8081/api/peliculas");
            const dataMovies = await resMovies.json();

            // Create Movie Map
            const movieMap = {};
            dataMovies.forEach(m => movieMap[m.id] = m);

            let filteredReviews = dataReviews;
            if (user?.rol === 'KID') {
                filteredReviews = dataReviews.filter(r => {
                    const m = movieMap[r.peliculaId];
                    return m && KID_TAGS.some(tag => m.titulo.includes(tag));
                });
            }

            setMovies(movieMap);
            setReviews(filteredReviews.reverse()); // Newest first
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div className="page-padding">
            <h1 className="page-title">Reseñas de la Comunidad</h1>

            <div className="all-reviews-grid" style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '2rem' }}>
                {reviews.map(r => {
                    const movie = movies[r.peliculaId];
                    return (
                        <div key={r.id} className="review-card glass-panel" style={{ marginBottom: 0, display: 'flex', flexDirection: 'column', height: '100%' }}>
                            {movie && (
                                <div style={{ display: 'flex', gap: '1rem', marginBottom: '1rem', borderBottom: '1px solid rgba(255,255,255,0.1)', paddingBottom: '10px' }}>
                                    <img src={movie.posterUrl} style={{ width: '50px', borderRadius: '4px' }} />
                                    <div>
                                        <h4 style={{ margin: 0, color: 'white' }}>{movie.titulo}</h4>
                                        <span style={{ fontSize: '0.8rem', color: '#888' }}>{movie.fechaEstreno?.substring(0, 4)}</span>
                                    </div>
                                </div>
                            )}

                            <div className="review-header">
                                <span className="username" style={{ color: 'var(--aurora-primary)' }}>{r.usuarioNombre || "Usuario"}</span>
                                <div className="review-stars-static">
                                    <span style={{ marginRight: '5px', fontWeight: 'bold', color: '#E50914' }}>{r.nota}/5</span>
                                    <Star size={14} fill="#E50914" stroke="none" />
                                </div>
                            </div>

                            <p className="review-body" style={{ flex: 1 }}>"{r.comentario || 'Sin comentario escrito.'}"</p>

                            <div style={{ marginTop: 'auto', paddingTop: '10px', fontSize: '0.75rem', color: '#555', textAlign: 'right' }}>
                                Publicado el {r.fecha}
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default ReviewsPage;
