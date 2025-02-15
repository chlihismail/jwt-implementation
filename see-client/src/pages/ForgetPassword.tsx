import React, { useEffect, useState } from "react";
import SubmitButton from "../components/common/buttons/SubmitButton";
import EmailInput from "../components/common/inputs/EmailInput";
import axios from "axios";
const FogetPassword: React.FC = () => {
    const [email, setEmail] = useState<string>("");
    const [message, setMessage] = useState<string>("");
    const [emailingError, setEmailingError] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        try{
            const response = await axios.post("http://localhost:8080/api/users/forget-password", {
                email,
            }, {
                headers: {
                    "Content-Type": "application/json",
                    },
            });

            if(response.status === 200){
                setMessage("Vérifiez votre boîte e-mail!");
            } else{
                console.error("Fail to send the email!");
                setEmailingError(true);
            }
        } catch(error) {
            console.error(error);
            setEmailingError(true);
        } finally {
            setLoading(false);
        }
    }

    useEffect(()=>{
        if(emailingError || message){
            const timer = setTimeout(()=>{
                setEmailingError(false);
                setMessage("")
            }, 5000);
            return ()=>clearTimeout(timer);
        }
    }, [emailingError, message])

    const handleEmail = (value: string ) => {
        setEmail(value);
    }
    return (
        <div className="flex flex-col items-center justify-center h-screen bg-gray-50">
            <h2 className="text-xl mb-4 text-center font-semibold text-sky-950">Entrez-vous votre e-mail</h2>
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
                        <SubmitButton
                            label={loading ? "Envoie en cours..." : "Envoyer"}
                        />
                    </div>
                    {message && <p className="text-sky-950 text-sm text-center">{message}</p>}
                    {emailingError && <h1 className="w-full m-auto rounded bg-red-700 text-white text-sm text-center font-medium p-1.5">Échec de l'envoi!</h1> }
                </form>

            </div>
        </div>
    );
}

export default FogetPassword;
