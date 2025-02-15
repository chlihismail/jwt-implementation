import React, { useContext, useEffect, useState } from "react";
import SubmitButton from "../components/common/buttons/SubmitButton";
import EmailInput from "../components/common/inputs/EmailInput";
import PasswordInput from "../components/common/inputs/PasswordInput";
import CheckboxInput from "../components/common/inputs/CheckboxInput";
import { AuthContext } from "../context/AuthContext";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login: React.FC = () => {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [rememberMe, setRememberMe] = useState<boolean>(false);
    const [loginError, setLoginError] = useState<boolean>(false);
    const { login } = useContext(AuthContext)!;
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        try{
            const response = await axios.post("http://localhost:8080/api/users/login", {
                email,
                password,
                rememberMe
            }, {
                headers: {
                    "Content-Type": "application/json",
                    },
            });

            const data = response.data;
            if(data.token && data.role){
                login(data.token, data.role, data.firstname, data.lastname);
                navigate("/")
            } else{
                console.error("Login failed!!");
                setLoginError(true);
            }
        } catch(error) {
            console.error(error);
            setLoginError(true);
        } finally {
            setLoading(false);
        }
    }

    useEffect(()=>{
        if(loginError){
            const timer = setTimeout(()=>{
                setLoginError(false);
            }, 5000);
            return ()=>clearTimeout(timer);
        }
    }, [loginError])

    const handleEmail = (value: string ) => {
        setEmail(value);
    }
    const handlePassword = (value: string) => {
        setPassword(value);
    }
    const handleRememberMe = (checked: boolean) => {
        setRememberMe(checked)
    }
    return (
        <div className="flex flex-col items-center justify-center h-screen bg-gray-50">
            <h2 className="text-xl mb-4 text-center font-semibold text-sky-950">Connectez-vous à votre compte</h2>
            <div className="bg-white p-8 rounded-lg shadow-md w-96">
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <EmailInput
                            value={email}
                            label="Adresse e-mail"
                            onChange={handleEmail}
                        />
                    </div>
                    <div className="mb-4">
                        <PasswordInput
                            value={password}
                            label="Mot de passe"
                            onChange={handlePassword}
                        />
                    </div>
                    <div className="mb-6 flex flex-col gap-3 sm:flex-row items-center justify-between">
                        <CheckboxInput
                            checked={rememberMe}
                            label="Se souvenir de moi"
                            onChange={handleRememberMe}
                        />
                        <a href="forget-password" className="text-sky-950 text-sm font-medium hover:text-sky-900">
                            Mot de passe oublié?
                        </a>
                    </div>
                   <div className="mb-4">
                        <SubmitButton
                            label={loading ? "Connexion en cours..." : "Se connecter"}
                        />
                    </div>
                    {loginError && <h1 className="w-full m-auto rounded bg-red-700 text-white text-sm text-center font-medium p-1.5">Échec de la connexion !</h1> }
                </form>

            </div>
        </div>
    );
}

export default Login;
