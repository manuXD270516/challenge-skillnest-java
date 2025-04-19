#!/bin/sh

# Lista de variables a reemplazar
envsubst '${VITE_API_URL}' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf

# Ejecuta nginx
exec nginx -g "daemon off;"
