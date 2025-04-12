import { createContext, useContext, useState, useEffect, ReactNode } from "react";

export interface SessionContextType {
  token: string | null;
  role: string | null;
  email: string | null;
  isLoaded: boolean;
  login: (token: string, role: string, email: string) => void;
  logout: () => void;
}

const defaultContext: SessionContextType = {
  token: null,
  role: null,
  email: null,
  isLoaded: false,
  login: () => {},
  logout: () => {},
};

export const SessionContext = createContext<SessionContextType>(defaultContext);

export const SessionProvider = ({ children }: { children: ReactNode }) => {
    const [token, setToken] = useState<string | null>(null);
    const [role, setRole] = useState<string | null>(null);
    const [email, setEmail] = useState<string | null>(null);
    const [isLoaded, setIsLoaded] = useState(false); // <-- NUEVO
  
    useEffect(() => {
      const storedToken = localStorage.getItem("token");
      const storedRole = localStorage.getItem("role");
      const storedEmail = localStorage.getItem("email");
  
      if (storedToken && storedRole && storedEmail) {
        setToken(storedToken);
        setRole(storedRole);
        setEmail(storedEmail);
      }
  
      setIsLoaded(true); // <== importante: al final
    }, []);
  
    const login = (newToken: string, newRole: string, newEmail: string) => {
      localStorage.setItem("token", newToken);
      localStorage.setItem("role", newRole);
      localStorage.setItem("email", newEmail);
      setToken(newToken);
      setRole(newRole);
      setEmail(newEmail);
    };
  
    const logout = () => {
      localStorage.clear();
      setToken(null);
      setRole(null);
      setEmail(null);
    };
  
    return (
      <SessionContext.Provider
        value={{ token, role, email, isLoaded, login, logout }}
      >
        {children}
      </SessionContext.Provider>
    );
  };
  

export const useSession = () => useContext(SessionContext);
