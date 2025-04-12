import axiosInstance from "../api/axiosInstance";

interface AuthResponse {
  token: string;
  email: string;
  role: string;
}

export async function loginUser(credentials: { email: string; password: string }) {
  const response = await axiosInstance.post<AuthResponse>("/auth/login", credentials);
  return response.data;
}

export async function registerUser(credentials: { email: string; password: string; role?: string }) {
  const response = await axiosInstance.post<AuthResponse>("/auth/register", credentials);
  return response.data;
}

export async function logoutRequest() {
  return axiosInstance.post("/auth/logout");
}
