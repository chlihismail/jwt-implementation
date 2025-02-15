import { useCallback, useContext, useEffect, useState } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import ActionButton from "../components/common/buttons/ActionButton";

const Home: React.FC = () => {
    const {isAuthenticated, logout} = useContext(AuthContext)!;
    const navigate = useNavigate();
    const [role, setRole] = useState<string | null>(null);
    const [firstname, setFirstname] = useState<string | null>(null);
    const [lastname, setLastname] = useState<string | null>(null);

    const handleLogout = useCallback(() => {
        // sessionStorage.removeItem('token');
        logout();
        navigate('/');
    }, [logout, navigate]);

    const handleLogin = () => {
        navigate('/login');
    };

    useEffect(() => {
        const role = sessionStorage.getItem('role');
        const firstname = sessionStorage.getItem('firstname');
        const lastname = sessionStorage.getItem('lastname');
        if (role && firstname && lastname) {
            setRole(role);
            setFirstname(firstname);
            setLastname(lastname);
        } else{
            handleLogout();
        }
    }, [handleLogout]);

    return (
        <div className="flex flex-col gap-2 items-center justify-center h-screen bg-gray-50">
            <p>Système d'authentification JWT.</p>
            {isAuthenticated ? (
                <div className="flex flex-col items-center">
                    <h1 className="font-bold mb-4 text-sky-950">Bonjour {firstname} {lastname}, vous avez le rôle {role}!</h1>
                    <ActionButton label="Se déconnecter" onClick={handleLogout}/>
                </div>
            ) : 
                <div>
                    <ActionButton label="Se connecter" onClick={handleLogin}/>
                </div>
            }
        </div>
    );
}

export default Home; 
