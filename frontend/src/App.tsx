import './App.css';
import { Auth0Provider } from '@auth0/auth0-react';
import { useAuth0 } from '@auth0/auth0-react';
import Finallogo from './assets/Finallogo.svg';
import Button from "./Button";

// This is your entry point
// Feel free to modify ANYTHING in this file
const domain = "dev-r7hj507hsi3jn34i.us.auth0.com";
const clientId = "g84SYUoiDvFIGevVYEBH5AcB4xaoHUFZ";

// Component per mostrar botons de Login/Logout
const AuthButtons = () => {
    const { loginWithRedirect, logout, isAuthenticated } = useAuth0();
    return (
        <div>
            {!isAuthenticated && (
                <button onClick={() => loginWithRedirect()}>Login</button>
            )}

            {isAuthenticated && (
                <>
                    <button onClick={() => logout({ returnTo: window.location.origin } as any)}>
                        Logout
                    </button>
                    <p>Has iniciat sessió correctament!</p>
                </>
            )}
        </div>
    );
};

function App() {
    return (
        <Auth0Provider
            domain={domain}
            clientId={clientId}
            authorizationParams={{
                redirect_uri: window.location.origin
            }}
        >
            <div className="App">
                <header className="App-header">
                    <img src={Finallogo} className="App-logo" alt="logo" />
                    <p>
                        Edit <code>src/App.tsx</code> and save to reload.
                    </p>
                    <a
                        className="App-link"
                        href="https://reactjs.org"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        Learn React
                    </a>
                    <AuthButtons /> {/* Afegim el component de login/logout */}
                    <Button />
                </header>
            </div>
        </Auth0Provider>
    );
}

export default App;
