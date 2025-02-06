import { ReactNode, useEffect, useState } from "react";
import { AuthContext } from "./AuthContext";

export const AuthProvider: React.FC<{children: ReactNode}> = ({children}) => {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(() => {return !!localStorage.getItem('token')});
    const [role, setRole] = useState<string | null>(() => {return localStorage.getItem('role')});
    const [token, setToken] = useState<string | null>(() => {return localStorage.getItem('token')});

  useEffect(() => {
    const userToken = localStorage.getItem('token');
    const userRole = localStorage.getItem('role');
    if (userToken && userRole) {
      setIsAuthenticated(true);
      setRole(userRole);
      setToken(userToken);
    }
  }, []);

  const login = (token: string, role: string) => {
    localStorage.setItem('token', token);
    localStorage.setItem('role', role);
    setIsAuthenticated(true);
    setRole(role);
    setToken(token);
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    setIsAuthenticated(false);
    setRole(null);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, role, login, logout, token }}>
      {children}
    </AuthContext.Provider>
  );
};
