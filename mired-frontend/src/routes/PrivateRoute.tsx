import { Navigate } from "react-router-dom";
import { useSession } from "../context/SessionContext";
import { JSX } from "react";
export default function PrivateRoute({ children }: { children: JSX.Element }) {
    const { token, isLoaded } = useSession();
  
    if (!isLoaded) return null; // O un spinner opcional
  
    return token ? children : <Navigate to="/login" replace />;
  }