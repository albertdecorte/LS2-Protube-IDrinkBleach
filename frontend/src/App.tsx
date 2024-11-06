import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import VideoList from './VideoList';
import Button from './Button';
import { Auth0Provider, useAuth0 } from '@auth0/auth0-react';
import Finallogo from './assets/Finallogo.svg';

// Auth0 configuration
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
                    <button onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}>
                        Logout
                    </button>
                    <p>Has iniciat sessió correctament!</p>
                </>
            )}
        </div>
    );
};

const App: React.FC = () => {
    return (
        <Auth0Provider
            domain={domain}
            clientId={clientId}
            authorizationParams={{
                redirect_uri: window.location.origin
            }}
        >
            <Router>
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
                    <main>
                        <Routes>
                            <Route path="/video-list" element={<VideoList />} />
                        </Routes>
                    </main>
                </div>
            </Router>
        </Auth0Provider>
    );
}

export default App;

