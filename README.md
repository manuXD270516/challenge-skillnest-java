# 📱 MiRed - Red Social Web

MiRed es una aplicación web tipo red social construida con un backend en **Spring Boot** y un frontend moderno en **React + Vite + TypeScript**, usando autenticación JWT y Docker para contenerización completa.

---

## 🚀 Características

- 🔐 Autenticación con JWT
- 🧾 Publicaciones con CRUD
- 👥 Sistema de amistad (solicitudes, aceptación y rechazo)
- 🧠 Roles de usuario (ADMIN, USER)
- 📦 Backend + Frontend dockerizados
- 💾 H2 Database (modo persistente o en memoria)
- 🌐 Navegación protegida y dinámica con React Router

---

## 📦 Estructura del Proyecto

```
my-network-challenge/
├── mired/                # Proyecto Spring Boot
│   ├── src/
│   └── Dockerfile
├── mired-frontend/               # Proyecto Vite + React + TS
│   ├── src/
│   └── Dockerfile
├── docker-compose.yml      # Orquestación de servicios
└── README.md
```

---

## 🛠️ Requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## ⚙️ Configuración rápida

### 1. Clonar el proyecto

```bash
git clone https://github.com/tu-usuario/mi-red.git
cd mi-red
```

### 2. Construir y levantar con Docker

```bash
docker compose up --build -d
```

Esto levantará:

- 🧠 Backend en `http://localhost:8080`
- 🎨 Frontend en `http://localhost:5173`
- 🧪 Consola H2 en `http://localhost:8080/h2-console`

---

## 👤 Usuarios por defecto (semillas)

| Email               | Rol   | Contraseña |
|--------------------|--------|-------------|
| user2@mired.com    | ADMIN | user2123    |
| user1@mired.com    |  USER | user1123    |

---

## 🔐 Endpoints principales

### Autenticación
| Método | Endpoint           | Descripción              |
|--------|--------------------|--------------------------|
| POST   | `/auth/login`      | Iniciar sesión           |
| POST   | `/auth/register`   | Registro de usuarios     |
| POST   | `/auth/logout`     | Cerrar sesión            |

### Publicaciones
| Método | Endpoint             | Descripción              |
|--------|----------------------|--------------------------|
| GET    | `/api/posts`         | Listar publicaciones     |
| GET    | `/api/posts/{id}`    | Ver detalle              |
| POST   | `/api/posts`         | Crear (solo ADMIN)       |
| PUT    | `/api/posts/{id}`    | Editar (solo ADMIN)      |
| DELETE | `/api/posts/{id}`    | Eliminar (solo ADMIN)    |

### Amistades
| Método | Endpoint                           | Descripción                      |
|--------|------------------------------------|----------------------------------|
| GET    | `/api/friends/search?query=`       | Buscar usuarios                  |
| POST   | `/api/friends/request/{id}`        | Enviar solicitud de amistad      |
| POST   | `/api/friends/accept/{id}`         | Aceptar solicitud recibida       |
| POST   | `/api/friends/reject/{id}`         | Rechazar solicitud               |
| GET    | `/api/friends/requests/received`   | Listar solicitudes recibidas     |
| GET    | `/api/friends/list`                | Ver lista de amigos confirmados  |
| DELETE | `/api/friends/{id}`                | Eliminar un amigo                |

---

## 🐳 docker-compose.yml

```yaml
version: '3.9'

services:
  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    networks:
      - mired-network

  frontend:
    build: ./frontend
    ports:
      - "5173:80"
    depends_on:
      - backend
    networks:
      - mired-network

networks:
  mired-network:
    driver: bridge
```

---

## 🧠 Consideraciones técnicas

- `axiosInstance.ts` está configurado para enrutar correctamente `/auth` y `/api` según el entorno.
- Se usan **dos instancias de Axios** en frontend:
  - `authInstance.ts` → para login, registro, logout.
  - `apiInstance.ts` → para publicaciones y amistades.

---

## 🧪 Base de datos H2

Accede a la consola de H2 en:
```
http://localhost:8080/h2-console
```

**JDBC URL:**
```
jdbc:h2:mem:mired_db
```

---

## 📌 Tips

- Si tienes errores de caché en Docker:
```bash
docker compose down -v --remove-orphans
docker builder prune -af
docker compose up --build
```

---

## 🧑‍💻 Autor

Desarrollado por Manuel Saavedra  
🌎 Challenge code con enfoque educativo - Abril 2025

---

## 📝 Licencia

MIT
