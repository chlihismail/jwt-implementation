import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Home from "../pages/Home";
import Login from "../pages/Login";
import ResetPassword from "../pages/ResetPassword";
import ForgetPassword from "../pages/ForgetPassword";

const AppRouter: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/reset-password" element={<ResetPassword/>}/>
                <Route path="/forget-password" element={<ForgetPassword/>}/>
            </Routes>
        </Router>
    );
}

export default AppRouter;
