# Etapa de construcción
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

# Copiar archivos del proyecto
COPY . .

# Copiar .env.production como .env para que Vite lo lea
COPY .env.production .env

# Compilar el frontend
RUN npm run build

# Etapa de servidor web
FROM nginx:stable-alpine
COPY --from=build /app/dist /usr/share/nginx/html

# Configuración básica NGINX (opcional)
COPY nginx/default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
