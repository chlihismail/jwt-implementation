import { createContext } from "react";

interface AuthContextType{
    isAuthenticated: boolean;
    role: string | null;
    login: (token: string, role: string) => void;
    logout: () => void;
    token: string | null;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);
