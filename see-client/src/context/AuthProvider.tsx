import { ReactNode, useEffect, useState } from "react";
import { AuthContext } from "./AuthContext";
import { jwtDecode } from "jwt-decode";

interface DecodedToken{
    exp: number;
}

export const AuthProvider: React.FC<{children: ReactNode}> = ({children}) => {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(() => {return !!sessionStorage.getItem('token')});
    const [firstname, setFirstname] = useState<string | null>(() => {return sessionStorage.getItem('firstname')});
    const [lastname, setLastname] = useState<string | null>(() => {return sessionStorage.getItem('lastname')});
    const [role, setRole] = useState<string | null>(() => {return sessionStorage.getItem('role')});
    const [token, setToken] = useState<string | null>(() => {return sessionStorage.getItem('token')});

    const isTokenValid = (token: string | null): boolean => {
        if(!token) return false;
        try{
            const decoded: DecodedToken = jwtDecode(token);
            return decoded.exp * 1000 > Date.now();
        }catch(error){
            console.error(error);
            return false;
        }
    };
    
    useEffect(() => {
        const userToken = sessionStorage.getItem('token');
        const userRole = sessionStorage.getItem('role');
        const firstname = sessionStorage.getItem('firstname');
        const lastname = sessionStorage.getItem('lastname');
        if (userToken && userRole && firstname && lastname && isTokenValid(token)) {
            setIsAuthenticated(true);
            setRole(userRole);
            setFirstname(firstname)
            setLastname(lastname)
            setToken(userToken);
        }else{
            logout();
        }
    }, [token]);

  const login = (token: string, role: string, firstname: string, lastname: string) => {
    sessionStorage.setItem('token', token);
    sessionStorage.setItem('role', role);
    sessionStorage.setItem('firstname', firstname);
    sessionStorage.setItem('lastname', lastname);
    setIsAuthenticated(true);
    setRole(role);
    setToken(token);
  };

  const logout = () => {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('role');
    sessionStorage.removeItem('firstname');
    sessionStorage.removeItem('lastname');
    setIsAuthenticated(false);
    setRole(null);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, role, firstname, lastname, login, logout, token }}>
      {children}
    </AuthContext.Provider>
  );
};
