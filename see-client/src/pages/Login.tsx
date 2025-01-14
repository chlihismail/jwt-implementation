import React, { useState } from "react";
import SubmitButton from "../components/common/buttons/SubmitButton";
import EmailInput from "../components/common/inputs/EmailInput";
import PasswordInput from "../components/common/inputs/PasswordInput";
import CheckboxInput from "../components/common/inputs/CheckboxInput";

const Login: React.FC = () => {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [rememberMe, setRememberMe] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
    }
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
            <h2 className="text-xl mb-4 text-center font-semibold text-sky-950">Sign in to your account</h2>
            <div className="bg-white p-8 rounded-lg shadow-md w-96">
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <EmailInput
                            value={email}
                            label="Email address"
                            onChange={handleEmail}
                        />
                    </div>
                    <div className="mb-4">
                        <PasswordInput
                            value={password}
                            label="Password"
                            onChange={handlePassword}
                        />
                    </div>
                    <div className="mb-6 flex flex-col gap-3 sm:flex-row items-center justify-between">
                        <CheckboxInput
                            checked={rememberMe}
                            label="Remember me"
                            onChange={handleRememberMe}
                        />
                        <a href="#" className="text-sky-950 text-sm font-medium hover:text-sky-900">
                            Forgot Password?
                        </a>
                    </div>
                    <div className="mb-4">
                        <SubmitButton
                            label={loading ? "Signing in..." : "Sign in"}
                        />
                    </div>
                </form>

            </div>
        </div>
    );
}

export default Login;
