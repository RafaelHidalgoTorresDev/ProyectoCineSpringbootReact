import React, { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import { Plus, Trash2, Pencil } from 'lucide-react';

const AVATARS = [
    "https://api.dicebear.com/7.x/avataaars/svg?seed=Felix",
    "https://api.dicebear.com/7.x/avataaars/svg?seed=Aneka",
    "https://api.dicebear.com/7.x/avataaars/svg?seed=Willow",
    "https://api.dicebear.com/7.x/avataaars/svg?seed=Bear",
    "https://api.dicebear.com/7.x/avataaars/svg?seed=Tigger", /* Kids option */
    "https://api.dicebear.com/7.x/avataaars/svg?seed=Leo", /* Kid */
    "https://api.dicebear.com/7.x/avataaars/svg?seed=Max",
    "https://api.dicebear.com/7.x/avataaars/svg?seed=Zoe"
];

const ProfileGate = () => {
    const { profiles, login, createProfile, deleteProfile, updateProfile } = useAuth();
    const [isManageMode, setIsManageMode] = useState(false); // Renamed for clarity vs 'editing a specific profile'
    const [showModal, setShowModal] = useState(false);

    // Form State
    const [editingId, setEditingId] = useState(null); // If null -> Creating. If set -> Updating.
    const [newName, setNewName] = useState("");
    const [isKid, setIsKid] = useState(false);
    const [selectedAvatar, setSelectedAvatar] = useState(AVATARS[0]);

    // Calculate taken avatars (excluding current user if editing)
    const takenAvatars = profiles
        .filter(p => p.id !== editingId)
        .map(p => p.avatar);

    const openCreateModal = () => {
        setEditingId(null);
        setNewName("");
        setIsKid(false);
        const nextAvailable = AVATARS.find(avi => !takenAvatars.includes(avi)) || AVATARS[0];
        setSelectedAvatar(nextAvailable);
        setShowModal(true);
    };

    const openEditModal = (profile) => {
        setEditingId(profile.id);
        setNewName(profile.username);
        setIsKid(profile.rol === 'KID');
        setSelectedAvatar(profile.avatar);
        setShowModal(true);
    };

    const handleSave = async (e) => {
        e.preventDefault();
        if (!newName.trim()) return;

        let success;
        if (editingId) {
            success = await updateProfile(editingId, newName, isKid, selectedAvatar);
        } else {
            success = await createProfile(newName, isKid, selectedAvatar);
        }

        if (success) {
            setShowModal(false);
            setIsManageMode(false);
        } else {
            alert("Error al guardar el perfil. Inténtalo de nuevo.");
        }
    };

    return (
        <div className="profile-gate">
            <h1 className="gate-title">¿Quién va a usar Rafa's Cinema?</h1>

            <div className="gate-grid">
                {profiles.map(profile => (
                    <div key={profile.id} className="gate-card-wrapper">
                        <div
                            className="gate-card"
                            onClick={() => {
                                if (isManageMode) {
                                    openEditModal(profile);
                                } else {
                                    login(profile);
                                }
                            }}
                        >
                            <img src={profile.avatar} alt={profile.username} className="gate-avatar" />

                            {/* OVERLAY FOR MANAGE MODE */}
                            {isManageMode && (
                                <div className="gate-overlay-edit">
                                    <div className="edit-icon-bg">
                                        <Pencil size={32} color="white" />
                                    </div>
                                    <span style={{ marginTop: '10px' }}>Editar</span>
                                </div>
                            )}
                        </div>
                        <span className="gate-name">{profile.username}</span>

                        {/* DELETE BUTTON OUTSIDE CARD FOR SAFETY? OR INSIDE MODAL? 
                            User asked for "managing", usually implies one screen. 
                            Let's put a small delete X on the card or keep it simple. 
                            I'll add a delete button inside the EDIT MODAL for better UX.
                        */}
                    </div>
                ))}

                {/* BOTÓN CREAR PERFIL */}
                {profiles.length < 5 && (
                    <div className="gate-card-wrapper">
                        <div className="gate-card add-profile" onClick={openCreateModal}>
                            <div className="add-icon-circle">
                                <Plus size={40} />
                            </div>
                        </div>
                        <span className="gate-name">Añadir Perfil</span>
                    </div>
                )}
            </div>

            <button
                className="gate-edit-btn"
                onClick={() => setIsManageMode(!isManageMode)}
            >
                {isManageMode ? "HECHO" : "ADMINISTRAR PERFILES"}
            </button>

            {/* MODAL (SHARED FOR CREATE & EDIT) */}
            {showModal && (
                <div className="gate-modal-overlay">
                    <div className="gate-modal">
                        <h2>{editingId ? "Editar Perfil" : "Nuevo Perfil"}</h2>

                        <div className="avatar-selector">
                            <p className="selector-label">Elige tu Avatar:</p>
                            <div className="avatars-grid">
                                {AVATARS.map((avi, idx) => {
                                    const isTaken = takenAvatars.includes(avi);
                                    return (
                                        <div key={idx} className="avatar-wrapper-modal">
                                            <img
                                                src={avi}
                                                className={`avatar-option-modal ${selectedAvatar === avi ? 'selected' : ''} ${isTaken ? 'disabled' : ''}`}
                                                onClick={() => !isTaken && setSelectedAvatar(avi)}
                                                alt="Avatar option"
                                                title={isTaken ? "Este avatar ya está en uso" : "Seleccionar"}
                                            />
                                            {isTaken && <div className="avatar-taken-overlay">✖</div>}
                                        </div>
                                    );
                                })}
                            </div>
                        </div>

                        <input
                            autoFocus
                            type="text"
                            placeholder="Nombre"
                            value={newName}
                            onChange={e => setNewName(e.target.value)}
                            className="gate-input"
                        />

                        <div
                            className={`kids-mode-card ${isKid ? 'active' : ''}`}
                            onClick={() => setIsKid(!isKid)}
                        >
                            <span className="kids-toggle-label">
                                {isKid ? "🧒 Perfil Infantil (Activado)" : "👤 Perfil Infantil (Kids)"}
                            </span>
                            <div className="toggle-switch">
                                <div className="toggle-knob"></div>
                            </div>
                        </div>

                        <div className="gate-modal-actions">
                            <button className="btn-cancel" onClick={() => setShowModal(false)}>Cancelar</button>
                            {editingId && (
                                <button
                                    className="btn-delete"
                                    onClick={async () => {
                                        if (confirm("¿Seguro que quieres borrar este perfil?")) {
                                            await deleteProfile(editingId);
                                            setShowModal(false);
                                        }
                                    }}
                                    style={{ backgroundColor: '#333', color: '#ff4444' }}
                                >
                                    Borrar
                                </button>
                            )}
                            <button className="btn-confirm" onClick={handleSave}>
                                {editingId ? "Actualizar" : "Guardar"}
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ProfileGate;

