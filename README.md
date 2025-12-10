# 🎬 Rafa's Cinema (Spring Boot + React)

¡Bienvenido a **Rafa's Cinema**! Este es un proyecto **Fullstack** que simula una plataforma de streaming al estilo Netflix/Disney+. 
Combina un **Backend potente en Java (Spring Boot)** con un **Frontend moderno en React** para gestionar películas, perfiles de usuario y modos infantiles.

---

## 🚀 Tecnologías Utilizadas

### Backend (Servidor)
*   **Java 17**
*   **Spring Boot 3.5.7**: Framework principal.
*   **Spring Data JPA**: Para la gestión de base de datos.
*   **H2 Database (File-based)**: Base de datos ligera y persistente almacenada en `/data`.
*   **Maven**: Gestor de dependencias.

### Frontend (Cliente)
*   **React 19**: Librería de UI.
*   **Vite**: Entorno de desarrollo ultrarrápido.
*   **Lucide React**: Iconografía moderna.
*   **React Router**: Navegación SPA.
*   **CSS Puro**: Estilos personalizados con diseño responsivo y animaciones.

---

## 🛠️ Instalación y Puesta en Marcha

Para probar el proyecto en tu máquina local, sigue estos pasos:

### 1. Clonar el repositorio
```bash
git clone https://github.com/RafaelHidalgoTorresDev/ProyectoCineSpringbootReact.git
cd ProyectoCineSpringbootReact
```

### 2. Arrancar el Backend (Puerto 8081)
El backend se encarga de la lógica, la base de datos y la API.
```bash
# En Windows (PowerShell/CMD):
.\mvnw.cmd spring-boot:run
```
*   Al iniciar, el sistema **cargará automáticamente** datos de prueba (películas y directores) si la base de datos está vacía.
*   La base de datos se guarda en la carpeta `data/` del proyecto, por lo que los datos persisten aunque reinicies.

### 3. Arrancar el Frontend (Puerto 5177)
Abre una nueva terminal en la carpeta `FrontEnd`:
```bash
cd FrontEnd
npm install
npm run dev
```

### 4. ¡A disfrutar!
Abre tu navegador y entra en:
👉 **http://localhost:5177**

---

## ✨ Funcionalidades Principales

### 1. Gestión de Perfiles
*   **Creación/Edición**: Puedes crear múltiples perfiles para distintos usuarios.
*   **Modo Niños (Kids Mode)**: Al activar esta opción, el perfil tendrá una interfaz simplificada y **solo mostrará contenido apto para niños** (películas de animación).
*   **Avatares**: Asignación automática de avatares aleatorios (Robots, Personas, etc.).

### 2. Catálogo de Películas
*   **Datos Reales**: El sistema carga películas famosas (Matrix, Interestelar, El Padrino, etc.) con sus carátulas y fondos oficiales de TMDB.
*   **Filtrado Inteligente**: Dependiendo de si entras con un perfil de adulto o niño, verás un catálogo diferente.

### 3. Persistencia
*   Todos los perfiles que crees se guardan en la base de datos H2 local.
*   Si cierras y vuelves a abrir la aplicación, **tus perfiles seguirán ahí**.

### 4. Interfaz "Premium"
*   Diseño inspirado en plataformas reales como HBO/Disney+.
*   Efectos de *hover*, transiciones suaves y diseño *glassmorphism*.

---

## 👨‍💻 Autor
**Rafael Hidalgo Torres**
*   Desarrollador Fullstack Java/React.
*   [GitHub](https://github.com/RafaelHidalgoTorresDev)

---
*Este proyecto fue generado y refinado utilizando asistencia de IA avanzada para la optimización de código y diseño.*
