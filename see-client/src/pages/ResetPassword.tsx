import React, {  useEffect, useState } from "react";
import SubmitButton from "../components/common/buttons/SubmitButton";
import PasswordInput from "../components/common/inputs/PasswordInput";
import axios from "axios";
import { useNavigate, useSearchParams } from "react-router-dom";

const ResetPassword: React.FC = () => {
    const [searchParams] = useSearchParams();
    const token = searchParams.get("random");

    const [newPassword, setNewPassword] = useState<string>("");
    const [confirmPassword, setConfirmPassword] = useState<string>("");
    const [resetError, setResetError] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        if (newPassword !== confirmPassword) {
            setMessage("Les mots de passe ne correspondent pas.")
            setLoading(false);
            return;
        }

        try{
            const response = await axios.post("http://localhost:8080/api/users/reset-password", {
                token,
                newPassword
            }, {
                    headers: {
                        "Content-Type": "application/json",
                    },
                });
            if(response.status === 200){
                navigate("/login");
            }else{
                setResetError(true);
            }
        } catch(error) {
            console.error(error);
            setResetError(true);
        } finally {
            setLoading(false);
        }
    };

    useEffect(()=>{
        if(resetError || message){
            const timer = setTimeout(()=>{
                setResetError(false);
                setMessage("")
            }, 5000);
            return ()=>clearTimeout(timer);
        }
    }, [resetError, message]);

    const handlePassword = (value: string) => {
        setNewPassword(value);
    }
    const handleConfirmPassword = (value: string) => {
        setConfirmPassword(value);
    }

    return (
        <div className="flex flex-col items-center justify-center h-screen bg-gray-50">
            <h2 className="text-xl mb-4 text-center font-semibold text-sky-950">Réinitialisation du mot de passe</h2>
            <div className="bg-white p-8 rounded-lg shadow-md w-96">
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <PasswordInput
                            value={newPassword}
                            label="Nouveau mot de passe"
                            onChange={handlePassword}
                        />
                    </div>
                    <div className="mb-4">
                        <PasswordInput
                            value={confirmPassword}
                            label="Confirmer mot de passe"
                            onChange={handleConfirmPassword}
                        />
                    </div>
                   <div className="mb-4">
                        <SubmitButton
                            label={loading ? "réinitialisation en cours..." : "réinitialiser"}
                        />
                    </div>
                    {message && <p className="text-sky-950 text-sm text-center">{message}</p>}
                    {resetError && <h1 className="w-full m-auto rounded bg-red-700 text-white text-sm text-center font-medium p-1.5">Erreur lors de la réinitialisation!</h1> }
                </form>

            </div>
        </div>
    );
}

export default ResetPassword;
