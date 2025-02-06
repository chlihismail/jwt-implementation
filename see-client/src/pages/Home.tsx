// import axios from "axios";
import { useCallback, useContext, useEffect, useState } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import ActionButton from "../components/common/buttons/ActionButton";

interface DecodedToken{
    sub: string;
}

const Home: React.FC = () => {
    const {isAuthenticated, logout} = useContext(AuthContext)!;
    const navigate = useNavigate();
    const [email, setEmail] = useState<string | null>(null);

    const handleLogout = useCallback(() => {
        localStorage.removeItem('token');
        logout();
        navigate('/');
    }, [logout, navigate]);

    const handleLogin = () => {
        navigate('/login');
    };

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            try {
                const decodedToken = jwtDecode<DecodedToken>(token);
                setEmail(decodedToken.sub);
            } catch (error) {
                console.error('Failed to decode token:', error);
                handleLogout();
            }
        }
    }, [handleLogout]);

    return (
        <div className="flex flex-col gap-2 items-center justify-center h-screen bg-gray-50">
            <p>JWT Authentication System.</p>
            {isAuthenticated ? (
                <div>
                    <h1 className="font-bold mb-4 text-sky-950">Welcome, {email}!</h1>
                    <ActionButton label="Sign out" onClick={handleLogout}/>
                </div>
            ) : 
                <ActionButton label="Sign in" onClick={handleLogin}/>
            }
        </div>
    );
}

export default Home; 
