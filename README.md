
# 📱 MiRed - Red Social Web

MiRed es una aplicación web tipo red social construida con un backend en **Spring Boot** y un frontend moderno en **React + Vite + TypeScript**, usando autenticación JWT y Docker para contenerización completa.

---

## 🚀 Demo en Producción

| Componente | URL |
|------------|-----|
| 🌐 **Frontend** | [https://challenge-skillnest-java-production-c634.up.railway.app](https://challenge-skillnest-java-production-c634.up.railway.app) |
| 🔧 **Backend**  | [https://challenge-skillnest-java-production.up.railway.app](https://challenge-skillnest-java-production.up.railway.app) |

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
├── mired-frontend/       # Proyecto Vite + React + TS
│   ├── src/
│   └── Dockerfile
├── docker-compose.yml    # Orquestación de servicios
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
git clone https://github.com/manuXD270516/challenge-skillnest-java my-network
cd my-network
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
| user1@mired.com    | USER  | user1123    |

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

## 📬 Colección de pruebas Postman

Accede a la colección completa aquí:

👉 [https://documenter.getpostman.com/view/2654210/2sB2cd5yUs](https://documenter.getpostman.com/view/2654210/2sB2cd5yUs)

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
