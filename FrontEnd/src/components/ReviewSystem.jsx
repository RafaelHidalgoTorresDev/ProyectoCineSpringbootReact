import React, { useState, useEffect } from 'react';
import { Star, Send, User } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

const ReviewSystem = ({ movie }) => {
    const { user } = useAuth();
    const [reviews, setReviews] = useState([]);
    const [rating, setRating] = useState(0);
    const [comment, setComment] = useState("");
    const [hoverRating, setHoverRating] = useState(0);

    const API_URL = "http://localhost:8081/api/criticas";

    useEffect(() => {
        if (movie?.id) fetchReviews();
    }, [movie]);

    const fetchReviews = async () => {
        try {
            const res = await fetch(API_URL);
            const data = await res.json();
            // Filtrar en frontend por ahora
            const movieReviews = data.filter(r => r.peliculaId === movie.id);
            setReviews(movieReviews.reverse()); // Most recent first
        } catch (err) {
            console.error(err);
        }
    };

    const handleSubmit = async () => {
        if (rating === 0) return alert("¡Debes seleccionar una puntuación!");

        const payload = {
            nota: rating,
            comentario: comment,
            usuarioId: user.id,
            peliculaId: movie.id,
            fecha: new Date().toISOString().split('T')[0]
        };

        try {
            const res = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (res.ok) {
                setComment("");
                setRating(0);
                fetchReviews();
            } else {
                alert("Error al enviar reseña");
            }
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div className="review-section">
            <h3 className="review-title">Opiniones de la Comunidad</h3>

            {/* FORMULARIO AURORA */}
            <div className="glass-panel review-form-layout">
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                        <img src={user.avatar} style={{ width: '40px', height: '40px', borderRadius: '50%', objectFit: 'cover', border: '2px solid rgba(255,255,255,0.1)' }} />
                        <div>
                            <span style={{ display: 'block', fontWeight: 'bold', color: 'white' }}>{user.username}</span>
                            <span style={{ display: 'block', fontSize: '0.8rem', color: '#888' }}>Comparte tu opinión</span>
                        </div>
                    </div>

                    <div className="star-selector">
                        {[1, 2, 3, 4, 5].map(star => (
                            <Star
                                key={star}
                                size={22}
                                fill={(hoverRating || rating) >= star ? "#ffd700" : "none"}
                                color={(hoverRating || rating) >= star ? "#ffd700" : "#666"}
                                onMouseEnter={() => setHoverRating(star)}
                                onMouseLeave={() => setHoverRating(0)}
                                onClick={() => setRating(star)}
                                style={{ cursor: 'pointer', transition: '0.2s' }}
                            />
                        ))}
                    </div>
                </div>

                <div className="review-input-wrapper">
                    <textarea
                        placeholder={`¿Qué te pareció ${movie.titulo}?`}
                        value={comment}
                        onChange={e => setComment(e.target.value)}
                        className="review-textarea"
                    />
                    <button
                        className="btn-send-review"
                        onClick={handleSubmit}
                        disabled={rating === 0 || !comment.trim()}
                        title="Publicar Reseña"
                    >
                        <Send size={18} />
                    </button>
                </div>
            </div>

            {/* LISTA DE REVIEWS MODERNAS */}
            <div className="reviews-grid">
                {reviews.length === 0 ? (
                    <div style={{ gridColumn: '1 / -1', textAlign: 'center', padding: '2rem', color: '#666' }}>
                        <p style={{ fontSize: '1.2rem', marginBottom: '0.5rem' }}>🍃</p>
                        <p>Aún no hay reseñas. ¡Sé el primero!</p>
                    </div>
                ) : (
                    reviews.map(r => (
                        <div key={r.id} className="review-card-modern">
                            <div className="review-head">
                                <div className="reviewer-meta">
                                    <div className="reviewer-avatar">
                                        <User size={18} />
                                    </div>
                                    <div>
                                        <span className="reviewer-name">{r.usuarioNombre || "Usuario"}</span>
                                        <span className="review-date-mini">{r.fecha}</span>
                                    </div>
                                </div>
                                <div className="review-rating-badge">
                                    <Star size={12} fill="#ffd700" stroke="none" />
                                    {r.nota}.0
                                </div>
                            </div>
                            <p className="review-content">"{r.comentario}"</p>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default ReviewSystem;
