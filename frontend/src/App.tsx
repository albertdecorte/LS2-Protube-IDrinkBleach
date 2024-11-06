import './App.css';
import { Auth0Provider } from '@auth0/auth0-react';
import { useAuth0 } from '@auth0/auth0-react';
import Finallogo from './assets/Finallogo.svg';
import Button from "./Button";
import VideoList from "./VideoList";
import VideoPlayer from './VideoPlayer';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

const domain = "dev-r7hj507hsi3jn34i.us.auth0.com";
const clientId = "g84SYUoiDvFIGevVYEBH5AcB4xaoHUFZ";

// Component to show login/logout buttons
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
            <Router>
                <div className="App">
                    <header className="App-header">
                        <img src={Finallogo} className="App-logo" alt="logo" />
                        <AuthButtons />
                        <Routes>
                            {/* Main video list route */}
                            <Route path="/" element={<VideoList />} />
                            {/* Video player route with dynamic ID */}
                            <Route path="/videos/:id" element={<VideoPlayer />} />
                        </Routes>
                        <Button />
                    </header>
                </div>
            </Router>
        </Auth0Provider>
    );
}

export default App;
