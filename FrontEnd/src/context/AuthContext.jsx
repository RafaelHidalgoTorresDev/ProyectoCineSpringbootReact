import React, { createContext, useState, useEffect, useContext } from 'react';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null); // Usuario activo (logueado)
    const [profiles, setProfiles] = useState([]); // Lista de usuarios desde BD
    const [loading, setLoading] = useState(true);

    const API_URL = "http://localhost:8081/api/usuarios";

    // Cargar perfiles al inicio
    useEffect(() => {
        fetchProfiles();
    }, []);

    const fetchProfiles = async () => {
        try {
            const res = await fetch(API_URL);
            if (!res.ok) throw new Error("Error fetching profiles");
            const data = await res.json();
            // Mapeamos para asegurarnos de tener un avatar (usamos un random o uno por defecto)
            const mapProfiles = data.map(u => ({
                ...u,
                avatar: u.avatar || (u.username.toLowerCase().includes('kid')
                    ? "https://i.pinimg.com/736x/2f/88/41/2f884178592d21a4c4de98e99829493f.jpg"
                    : `https://api.dicebear.com/7.x/avataaars/svg?seed=${u.username}&backgroundColor=b6e3f4`)
            }));
            setProfiles(mapProfiles);
        } catch (err) {
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const login = (profile) => {
        setUser(profile);
        // Podríamos guardar en localStorage aquí
    };

    const logout = () => {
        setUser(null);
    };

    const createProfile = async (name, isKid = false, avatarUrl = "") => {
        try {
            const newUser = {
                username: name,
                email: `${name.toLowerCase().replace(/\s/g, '')}@aurora.com`,
                password: "password123", // Cumple con min 8 chars
                rol: isKid ? "KID" : "USER",
                avatar: avatarUrl || (isKid
                    ? "https://i.pinimg.com/736x/2f/88/41/2f884178592d21a4c4de98e99829493f.jpg"
                    : `https://api.dicebear.com/7.x/avataaars/svg?seed=${name}&backgroundColor=b6e3f4`)
            };

            const res = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(newUser)
            });

            if (res.ok) {
                await fetchProfiles(); // Recargar lista
                return true;
            }
        } catch (err) {
            console.error("Error creating profile:", err);
            return false;
        }
    };

    const deleteProfile = async (id) => {
        try {
            const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
            if (res.ok) {
                setProfiles(prev => prev.filter(p => p.id !== id));
                if (user && user.id === id) logout();
                return true;
            } else {
                console.error("Failed to delete profile. Status:", res.status);
                return false;
            }
        } catch (err) {
            console.error("Error deleting profile:", err);
            return false;
        }
    };

    const updateProfile = async (id, name, isKid, avatarUrl) => {
        try {
            const updatedUser = {
                username: name,
                email: `${name.toLowerCase().replace(/\s/g, '')}@aurora.com`,
                // password, rol, avatar, etc. are handled by backend or mapped here? 
                // Careful: Backend expects UsuarioCreateUpdate DTO. It likely needs password field even if dummy.
                password: "password123",
                rol: isKid ? "KID" : "USER",
                avatar: avatarUrl
            };

            const res = await fetch(`${API_URL}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedUser)
            });

            if (res.ok) {
                await fetchProfiles();
                return true;
            }
        } catch (err) {
            console.error("Error updating profile:", err);
            return false;
        }
    };

    return (
        <AuthContext.Provider value={{ user, profiles, loading, login, logout, createProfile, deleteProfile, updateProfile }}>
            {children}
        </AuthContext.Provider>
    );
};
