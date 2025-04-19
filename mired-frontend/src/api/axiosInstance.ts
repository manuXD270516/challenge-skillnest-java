import axios from "axios";

const baseURL =
  typeof window !== "undefined" && (window as any).VITE_API_URL
    ? (window as any).VITE_API_URL
    : import.meta.env.VITE_API_URL;

const instance = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: false
});

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token") || sessionStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default instance;
