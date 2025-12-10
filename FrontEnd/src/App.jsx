import React, { useState, useEffect, useRef } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useLocation } from 'react-router-dom';
import { Play, Info, Search, X, ChevronLeft, ChevronRight, Home as HomeIcon, Film, Zap, Star, Calendar, MessageSquare } from 'lucide-react';
import { AuthProvider, useAuth } from './context/AuthContext';
import AuroraBackground from './components/Layout/AuroraBackground';
import ProfileGate from './components/Auth/ProfileGate';
import ReviewSystem from './components/ReviewSystem';
import ReviewsPage from './components/Review/ReviewsPage';
import './App.css';

// CONSTANTS
const FALLBACK_POSTER = "https://images.unsplash.com/photo-1536440136628-849c177e76a1?auto=format&fit=crop&w=500&q=80";
const FALLBACK_BACKDROP = "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?auto=format&fit=crop&w=1920&q=80";
const AVATAR_KIDS_BACKUP = "https://upload.wikimedia.org/wikipedia/en/thumb/3/36/Russell_from_Up.png/220px-Russell_from_Up.png";

const ScrollToTop = () => {
    const { pathname } = useLocation();
    useEffect(() => { window.scrollTo(0, 0); }, [pathname]);
    return null;
};

// --- SIDEBAR ---
const Sidebar = ({ searchTerm, setSearchTerm, isSearchOpen, setIsSearchOpen }) => {
    const location = useLocation();
    const isActive = (path) => location.pathname === path;
    const { user, logout } = useAuth();

    return (
        <nav className="sidebar">
            <div className="sidebar-top">
                <Link to="/" className="sidebar-logo" onClick={() => setSearchTerm("")}>
                    <img src="rafas_cinema_logo.png" alt="Rafa's Cinema" className="brand-logo-img" />
                </Link>

                <div className="sidebar-links">
                    <Link to="/" className={`sidebar-link ${isActive('/') ? 'active' : ''}`}>
                        <HomeIcon size={24} />
                        <span className="link-text">Inicio</span>
                    </Link>
                    <Link to="/peliculas" className={`sidebar-link ${isActive('/peliculas') ? 'active' : ''}`}>
                        <Film size={24} />
                        <span className="link-text">Cine</span>
                    </Link>
                    {user?.rol !== 'KID' ? (
                        <>
                            <Link to="/novedades" className={`sidebar-link ${isActive('/novedades') ? 'active' : ''}`}>
                                <Zap size={24} />
                                <span className="link-text">Novedades</span>
                            </Link>
                            <Link to="/resenas" className={`sidebar-link ${isActive('/resenas') ? 'active' : ''}`}>
                                <MessageSquare size={24} />
                                <span className="link-text">Reseñas</span>
                            </Link>
                        </>
                    ) : (
                        <Link to="/resenas" className={`sidebar-link ${isActive('/resenas') ? 'active' : ''}`}>
                            <MessageSquare size={24} />
                            <span className="link-text">Reseñas</span>
                        </Link>
                    )}
                </div>
            </div>

            <div className="sidebar-bottom">
                <div className={`sidebar-search ${isSearchOpen ? 'open' : ''}`}>
                    <Search className="search-icon-btn" size={24} onClick={() => setIsSearchOpen(!isSearchOpen)} />
                    <div className="search-input-wrapper">
                        <input
                            type="text"
                            placeholder="Buscar título..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="search-input"
                        />
                        {searchTerm && <X size={16} className="search-clear" onClick={() => setSearchTerm("")} style={{ cursor: 'pointer' }} />}
                    </div>
                </div>

                <div className="profile-wrapper" onClick={logout} title={`Cerrar sesión de ${user?.username}`}>
                    <img
                        src={user?.avatar}
                        alt="Profile"
                        className="nav-avatar"
                        onError={(e) => e.target.src = AVATAR_KIDS_BACKUP}
                    />
                </div>
            </div>
        </nav>
    );
};

// --- AUTHENTICATED APP SHELL ---
const AuthenticatedApp = () => {
    const { user } = useAuth();
    const [allMovies, setAllMovies] = useState([]);
    const [filteredMovies, setFilteredMovies] = useState([]);
    const [heroMovie, setHeroMovie] = useState(null);
    const [selectedMovie, setSelectedMovie] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");
    const [isSearchOpen, setIsSearchOpen] = useState(false);

    const KID_TAGS = ["Animated", "Family", "Cartoon", "Disney", "Pixar", "Dreamworks", "Soul", "Wall-E", "Up", "Toy Story", "Nemo", "Rey León", "Coco", "Monstruos", "Increíbles", "Ratatouille", "Cars", "Shrek", "Frozen", "Gru", "Panda", "Spider-Man", "Mario", "Encanto", "Del Revés"];

    useEffect(() => {
        fetch("http://localhost:8081/api/peliculas")
            .then(res => res.json())
            .then(data => setAllMovies(data))
            .catch(err => console.error("Error fetching movies:", err));
    }, []);

    useEffect(() => {
        if (!user || allMovies.length === 0) return;
        let moviesToShow = allMovies;

        if (user.rol === "KID") {
            moviesToShow = allMovies.filter(m => KID_TAGS.some(tag => m.titulo.includes(tag)));
            if (moviesToShow.length > 0) setHeroMovie(moviesToShow[0]);
        } else {
            const adultHero = allMovies.find(m => !KID_TAGS.some(tag => m.titulo.includes(tag)));
            setHeroMovie(adultHero || allMovies[0]);
        }
        setFilteredMovies(moviesToShow);
    }, [user, allMovies]);

    const handleMovieClick = (movie) => setSelectedMovie(movie);
    const handlePlay = () => alert(`🍿 Reproduciendo: ${heroMovie?.titulo || "Pelicula"}\n\nDisfruta de tu sesión, ${user.username}.`);

    const HomeView = () => (
        <>
            {heroMovie && (
                <header className="banner">
                    <img
                        src={heroMovie.backdropUrl}
                        className="banner-img"
                        alt={heroMovie.titulo}
                        onError={(e) => e.target.src = FALLBACK_BACKDROP}
                    />
                    <div className="banner-fade"></div>
                    <div className="banner-content">
                        <h1 className="banner-title">{heroMovie.titulo}</h1>
                        <p className="banner-description">{heroMovie.sinopsis}</p>
                        <div className="banner-buttons">
                            <button className="btn btn-white" onClick={handlePlay}><Play fill="black" size={20} /> VER AHORA</button>
                            <button className="btn btn-grey" onClick={() => handleMovieClick(heroMovie)}><Info size={20} /> MÁS INFO</button>
                        </div>
                    </div>
                </header>
            )}
            <div className="rows-container">
                <Row title={user.rol === 'KID' ? "Tus Favoritas" : "Tendencias Ahora"} data={filteredMovies} onMovieClick={handleMovieClick} />
                <Row title="Aclamadas por la crítica" data={[...filteredMovies].reverse()} onMovieClick={handleMovieClick} />
                {user.rol !== 'KID' && <Row title="Acción y Adrenalina" data={filteredMovies} onMovieClick={handleMovieClick} />}
            </div>
        </>
    );

    const GridView = ({ title, data }) => (
        <div className="page-padding">
            <h1 className="page-title">{title}</h1>
            <div className="grid-container">
                {data.map(p => (
                    <div key={p.id} className="grid-item" onClick={() => handleMovieClick(p)}>
                        <img src={p.posterUrl} onError={(e) => e.target.src = FALLBACK_POSTER} alt={p.titulo} />
                    </div>
                ))}
            </div>
        </div>
    );

    return (
        <Router>
            <ScrollToTop />
            <div className="app">
                <Sidebar searchTerm={searchTerm} setSearchTerm={setSearchTerm} isSearchOpen={isSearchOpen} setIsSearchOpen={setIsSearchOpen} />
                <main className="main-content">
                    {searchTerm.length > 0 ? (
                        <GridView title={`Resultados: "${searchTerm}"`} data={filteredMovies.filter(p => p.titulo.toLowerCase().includes(searchTerm.toLowerCase()))} />
                    ) : (
                        <Routes>
                            <Route path="/" element={<HomeView />} />
                            <Route path="/peliculas" element={<GridView title="Catálogo Completo" data={filteredMovies} />} />
                            <Route path="/novedades" element={<GridView title="Recién Llegadas" data={[...filteredMovies].sort((a, b) => new Date(b.fechaEstreno) - new Date(a.fechaEstreno))} />} />
                            <Route path="/resenas" element={<ReviewsPage />} />
                            <Route path="*" element={<HomeView />} />
                        </Routes>
                    )}
                </main>

                {/* GLOBAL MODAL - Accessible from any route */}
                {selectedMovie && (
                    <div className="modal-overlay" onClick={() => setSelectedMovie(null)}>
                        <div className="modal-content" onClick={e => e.stopPropagation()}>
                            <button className="modal-close" onClick={() => setSelectedMovie(null)}><X size={24} /></button>
                            <div className="modal-hero">
                                <img className="modal-img-bg" src={selectedMovie.backdropUrl} onError={(e) => e.target.src = FALLBACK_BACKDROP} alt="" />
                                <div className="modal-gradient"></div>
                                <div className="modal-hero-content">
                                    <h1>{selectedMovie.titulo}</h1>
                                    <button className="btn btn-white" onClick={() => alert("Reproduciendo...")}><Play fill="black" size={20} /> Reproducir</button>
                                </div>
                            </div>
                            <div className="modal-body">
                                <div className="modal-meta">
                                    <span className="match">98% Match</span>
                                    <span style={{ display: 'flex', alignItems: 'center', gap: '5px' }}><Calendar size={18} /> {selectedMovie.fechaEstreno?.substring(0, 4) || '2024'}</span>
                                    <span className="hd-badge">4K HDR</span>
                                    <span style={{ display: 'flex', alignItems: 'center', gap: '5px', color: '#ffd700' }}><Star size={18} fill="#ffd700" /> {selectedMovie.valoracion}/10</span>
                                </div>
                                <p style={{ lineHeight: '1.6', fontSize: '1.1rem', color: '#ccc' }}>{selectedMovie.sinopsis}</p>
                                <ReviewSystem movie={selectedMovie} />
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </Router>
    );
};

const Row = ({ title, data, onMovieClick }) => {
    const rowRef = useRef(null);
    const scroll = (offset) => { if (rowRef.current) rowRef.current.scrollBy({ left: offset, behavior: 'smooth' }); };
    return (
        <div className="row">
            <h2>{title}</h2>
            <div className="row-slider-container">
                <button className="slider-arrow left" onClick={() => scroll(-500)}><ChevronLeft size={40} /></button>
                <div className="row-posters" ref={rowRef}>
                    {data.map(p => (
                        <img key={p.id} className="poster" src={p.posterUrl} onClick={() => onMovieClick(p)} onError={(e) => e.target.src = FALLBACK_POSTER} />
                    ))}
                </div>
                <button className="slider-arrow right" onClick={() => scroll(500)}><ChevronRight size={40} /></button>
            </div>
        </div>
    );
};

// --- MAIN WRAPPER (Handles Auth State) ---
const AppContent = () => {
    const { user, loading } = useAuth();
    if (loading) return <div style={{ height: '100vh', display: 'flex', justifyContent: 'center', alignItems: 'center', background: 'black' }}>Loading Aurora...</div>;
    return user ? <AuthenticatedApp /> : <AuroraBackground><ProfileGate /></AuroraBackground>;
};

export default function App() {
    return (
        <AuthProvider>
            <AppContent />
        </AuthProvider>
    );
}