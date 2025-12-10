import React, { useState, useEffect, useRef } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useLocation } from 'react-router-dom';
// AÑADIDOS ICONOS NUEVOS: Home, Film, Zap (para novedades)
import { Play, Info, Search, X, ChevronLeft, ChevronRight, Home as HomeIcon, Film, Zap } from 'lucide-react';
import './App.css';

// IMÁGENES SEGURAS
const AVATAR_ADULT = "https://image.tmdb.org/t/p/w200/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg";
const AVATAR_KIDS = "https://i.pinimg.com/736x/21/92/78/2192782e4604b39794d03378544c7784.jpg";
const AVATAR_KIDS_BACKUP = "https://upload.wikimedia.org/wikipedia/en/thumb/3/36/Russell_from_Up.png/220px-Russell_from_Up.png";
const FALLBACK_POSTER = "https://images.unsplash.com/photo-1536440136628-849c177e76a1?auto=format&fit=crop&w=500&q=80";
const FALLBACK_BACKDROP = "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?auto=format&fit=crop&w=1920&q=80";

const ScrollToTop = () => {
    const { pathname } = useLocation();
    useEffect(() => { window.scrollTo(0, 0); }, [pathname]);
    return null;
};

// --- NUEVO COMPONENTE: SIDEBAR (BARRA LATERAL) ---
const Sidebar = ({ searchTerm, setSearchTerm, isSearchOpen, setIsSearchOpen, currentProfile, onChangeProfile }) => {
    const location = useLocation();

    // Función para saber si un enlace está activo
    const isActive = (path) => location.pathname === path;

    return (
        <nav className="sidebar">
            <div className="sidebar-top">
                <Link to="/" className="sidebar-logo" onClick={() => setSearchTerm("")}>RC</Link> {/* Logo corto para sidebar */}

                <ul className="sidebar-links">
                    <li>
                        <Link to="/" className={`sidebar-link ${isActive('/') ? 'active' : ''}`}>
                            <HomeIcon size={24} />
                            <span className="link-text">Inicio</span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/peliculas" className={`sidebar-link ${isActive('/peliculas') ? 'active' : ''}`}>
                            <Film size={24} />
                            <span className="link-text">Películas</span>
                        </Link>
                    </li>
                    {currentProfile?.id !== 'kids' && (
                        <li>
                            <Link to="/novedades" className={`sidebar-link ${isActive('/novedades') ? 'active' : ''}`}>
                                <Zap size={24} />
                                <span className="link-text">Novedades</span>
                            </Link>
                        </li>
                    )}
                </ul>
            </div>

            <div className="sidebar-bottom">
                {/* Buscador adaptado al lateral */}
                <div className={`sidebar-search ${isSearchOpen ? 'open' : ''}`}>
                    <Search className="search-icon-btn" size={24} onClick={() => setIsSearchOpen(!isSearchOpen)} />
                    <div className="search-input-wrapper">
                        <input
                            type="text"
                            placeholder="Buscar..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="search-input"
                        />
                        {searchTerm && <X className="search-clear" size={18} onClick={() => setSearchTerm("")} />}
                    </div>
                </div>

                <div className="profile-wrapper" onClick={onChangeProfile} title={"Perfil: " + currentProfile?.name}>
                    <img src={currentProfile?.avatar} alt="Profile" className="nav-avatar" onError={(e) => e.target.src = AVATAR_KIDS_BACKUP} />
                </div>
            </div>
        </nav>
    );
};

// --- APP ---
export default function App() {
    const [allMovies, setAllMovies] = useState([]);
    const [filteredMovies, setFilteredMovies] = useState([]);
    const [heroMovie, setHeroMovie] = useState(null);
    const [selectedMovie, setSelectedMovie] = useState(null);
    const [currentProfile, setCurrentProfile] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");
    const [isSearchOpen, setIsSearchOpen] = useState(false);

    const KIDS_KEYWORDS = ["Soul", "Wall-E", "Up", "Toy Story", "Nemo", "Rey León", "Coco", "Monstruos", "Increíbles", "Ratatouille", "Cars", "Shrek", "Frozen", "Gru", "Panda", "Spider-Man", "Mario", "Encanto", "Del Revés"];

    useEffect(() => {
        fetch("http://localhost:8081/api/peliculas")
            .then(res => res.json())
            .then(data => setAllMovies(data))
            .catch(err => console.error("Error al cargar películas:", err));
    }, []);

    useEffect(() => {
        if (!currentProfile || allMovies.length === 0) return;
        let moviesToShow = allMovies;
        if (currentProfile.id === "kids") {
            moviesToShow = allMovies.filter(m => KIDS_KEYWORDS.some(k => m.titulo.includes(k)));
            if (moviesToShow.length > 0) setHeroMovie(moviesToShow[0]);
        } else {
            const adultHero = allMovies.find(m => !KIDS_KEYWORDS.some(k => m.titulo.includes(k)));
            setHeroMovie(adultHero || allMovies[0]);
        }
        setFilteredMovies(moviesToShow);
    }, [currentProfile, allMovies]);

    const handlePlay = () => alert("🍿 ¡Función premium no activada!");

    // SELECCIÓN DE PERFIL
    if (!currentProfile) {
        return (
            <div className="profile-screen">
                <h1>¿Quién eres?</h1>
                <div className="profiles-container">
                    <div className="profile-card" onClick={() => setCurrentProfile({ id: "adult", name: "Rafa", avatar: AVATAR_ADULT })}>
                        <img src={AVATAR_ADULT} alt="adult" onError={(e) => e.target.src = FALLBACK_POSTER} />
                        <span>Rafa</span>
                    </div>
                    <div className="profile-card" onClick={() => setCurrentProfile({ id: "kids", name: "Kids", avatar: AVATAR_KIDS })}>
                        <img src={AVATAR_KIDS} alt="kids" onError={(e) => e.target.src = AVATAR_KIDS_BACKUP} />
                        <span>Niños</span>
                    </div>
                </div>
            </div>
        );
    }

    const Modal = () => {
        if (!selectedMovie) return null;
        return (
            <div className="modal-overlay" onClick={() => setSelectedMovie(null)}>
                <div className="modal-content" onClick={e => e.stopPropagation()}>
                    <button className="modal-close" onClick={() => setSelectedMovie(null)}><X /></button>
                    <div className="modal-hero">
                        <img className="modal-img-bg" src={selectedMovie.backdropUrl} onError={(e) => e.target.src = FALLBACK_BACKDROP} />
                        <div className="modal-gradient"></div>
                        <div className="modal-hero-content">
                            <h1>{selectedMovie.titulo}</h1>
                            <button className="btn btn-white" onClick={handlePlay}><Play fill="black" size={20} /> Reproducir</button>
                        </div>
                    </div>
                    <div className="modal-body">
                        <div className="modal-left">
                            <div className="modal-meta">
                                <span className="match">98% para ti</span>
                                <span>{selectedMovie.fechaEstreno}</span>
                                <span className="hd-badge">HD</span>
                            </div>
                            <p className="modal-synopsis">{selectedMovie.sinopsis}</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    };

    const Home = () => {
        if (searchTerm.length > 0) {
            return renderSearchResults();
        }

        return (
            <>
                {heroMovie && (
                    <header className="banner">
                        <img
                            src={heroMovie.backdropUrl}
                            className="banner-img"
                            alt={heroMovie.titulo}
                            onError={(e) => {
                                if (e.target.src !== heroMovie.posterUrl) e.target.src = heroMovie.posterUrl;
                                else e.target.src = FALLBACK_BACKDROP;
                            }}
                        />
                        <div className="banner-fade"></div>
                        <div className="banner-content">
                            <h1 className="banner-title">{heroMovie.titulo}</h1>
                            <p className="banner-description">{heroMovie.sinopsis}</p>
                            <div className="banner-buttons">
                                <button className="btn btn-white" onClick={handlePlay}><Play fill="black" size={24} /> Reproducir</button>
                                <button className="btn btn-grey" onClick={() => setSelectedMovie(heroMovie)}><Info size={24} /> Más info</button>
                            </div>
                        </div>
                    </header>
                )}
                <div className="rows-container">
                    <Row title={currentProfile.id === 'kids' ? "Favoritas de los peques" : "Tendencias ahora"} data={filteredMovies} />
                    <Row title={currentProfile.id === 'kids' ? "Aventuras animadas" : "Aclamadas por la crítica"} data={[...filteredMovies].reverse()} />
                    {currentProfile.id !== 'kids' && <Row title="Acción y Suspense" data={filteredMovies} />}
                </div>
            </>
        );
    };

    const MoviesPage = () => (
        <div className="page-padding">
            <h1 className="page-title">{currentProfile.id === "kids" ? "Películas Infantiles" : "Todas las Películas"}</h1>
            <div className="grid-container">
                {filteredMovies.map(p => (
                    <div key={p.id} className="grid-item" onClick={() => setSelectedMovie(p)}>
                        <img src={p.posterUrl} onError={(e) => e.target.src = FALLBACK_POSTER} />
                    </div>
                ))}
            </div>
        </div>
    );

    const NewsPage = () => {
        const sorted = [...allMovies].sort((a,b) => new Date(b.fechaEstreno) - new Date(a.fechaEstreno));
        return (
            <div className="page-padding">
                <h1 className="page-title">Últimas Novedades</h1>
                <div className="grid-container">
                    {sorted.map(p => (
                        <div key={p.id} className="grid-item" onClick={() => setSelectedMovie(p)}>
                            <img src={p.posterUrl} onError={(e) => e.target.src = FALLBACK_POSTER} />
                            <span className="new-badge">NUEVO</span>
                        </div>
                    ))}
                </div>
            </div>
        );
    };

    const Row = ({ title, data }) => {
        const rowRef = useRef(null);
        const scroll = (offset) => { if (rowRef.current) rowRef.current.scrollBy({ left: offset, behavior: 'smooth' }); };
        return (
            <div className="row">
                <h2>{title}</h2>
                <div className="row-slider-container">
                    <button className="slider-arrow left" onClick={() => scroll(-500)}><ChevronLeft/></button>
                    <div className="row-posters" ref={rowRef}>
                        {data.map(p => (
                            <img key={p.id} className="poster" src={p.posterUrl} onClick={() => setSelectedMovie(p)} onError={(e) => e.target.src = FALLBACK_POSTER} />
                        ))}
                    </div>
                    <button className="slider-arrow right" onClick={() => scroll(500)}><ChevronRight/></button>
                </div>
            </div>
        );
    };

    function renderSearchResults() {
        const resultados = filteredMovies.filter(p => p.titulo.toLowerCase().includes(searchTerm.toLowerCase()));
        return (
            <div className="page-padding search-results-page">
                <h2>Resultados para: "{searchTerm}"</h2>
                <div className="grid-container">
                    {resultados.map(p => (
                        <div key={p.id} className="grid-item" onClick={() => setSelectedMovie(p)}>
                            <img src={p.posterUrl} onError={(e) => e.target.src = FALLBACK_POSTER} />
                        </div>
                    ))}
                </div>
            </div>
        );
    }

    return (
        <Router>
            <ScrollToTop />
            <div className="app">
                {/* SIDEBAR A LA IZQUIERDA */}
                <Sidebar searchTerm={searchTerm} setSearchTerm={setSearchTerm} isSearchOpen={isSearchOpen} setIsSearchOpen={setIsSearchOpen} currentProfile={currentProfile} onChangeProfile={() => setCurrentProfile(null)} />

                {/* CONTENIDO PRINCIPAL A LA DERECHA */}
                <main className="main-content">
                    {searchTerm.length > 0 ? renderSearchResults() : (
                        <Routes>
                            <Route path="/" element={<Home />} />
                            <Route path="/peliculas" element={<MoviesPage />} />
                            <Route path="/novedades" element={<NewsPage />} />
                            <Route path="*" element={<Home />} />
                        </Routes>
                    )}
                </main>
            </div>
        </Router>
    );
}