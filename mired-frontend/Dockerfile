# Etapa de build
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# Etapa de nginx
FROM nginx:stable-alpine
WORKDIR /usr/share/nginx/html

# Copiamos el contenido compilado
COPY --from=build /app/dist .

# Copiamos y procesamos el template de nginx con envsubst
COPY nginx/default.conf.template /etc/nginx/templates/default.conf.template

# Permite que NGINX sustituya las variables de entorno al arrancar
ENV VITE_API_URL=/

COPY ./public/config.js ./config.js


EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
