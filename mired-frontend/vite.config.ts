import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import dotenv from 'dotenv';

// Cargar el entorno actual
dotenv.config({ path: `.env.${process.env.NODE_ENV || 'local'}` });

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    host: true,
    proxy: {
      '/api': {
        target: process.env.VITE_API_URL,
        changeOrigin: true,
        secure: false,
      },
      '/auth': {
        target: process.env.VITE_API_URL,
        changeOrigin: true,
        secure: false,
      },
    },
  }
});
