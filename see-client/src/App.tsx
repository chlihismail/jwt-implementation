
import { AuthProvider } from "./context/AuthProvider";
import AppRouter from "./routes/Router";

const App: React.FC = () => {
    return (
        <AuthProvider>
            <AppRouter />
        </AuthProvider>
    );
}

export default App;
