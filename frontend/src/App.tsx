import './App.css';
import {Auth0Provider, useAuth0} from '@auth0/auth0-react';
import Finallogo from './assets/Finallogo.svg';
import VideoList from "./VideoList";
import VideoPlayer from './VideoPlayer';
import LoginButton from './assets/LoginButton.svg';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
//import IchigoLogo from './assets/IchigoLogo.svg';

const domain = "dev-r7hj507hsi3jn34i.us.auth0.com";
const clientId = "g84SYUoiDvFIGevVYEBH5AcB4xaoHUFZ";

// Component to show login/logout buttons
const AuthButtons = () => {
    const {loginWithRedirect, logout, isAuthenticated} = useAuth0();
    return (
        <div style={{ background: 'transparent', padding: 0 }}>
            {!isAuthenticated && (
                <button onClick={() => loginWithRedirect()}
                        style={{
                            backgroundColor: "transparent",
                            border: "none",
                            padding: 0,
                            margin: 0
                        }}
                >
                    <img src={LoginButton} className="button-icon" alt="logo" />
                </button>
            )}
            {isAuthenticated && (
                <>
                    <button onClick={() => logout({returnTo: window.location.origin} as any)}>
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
                        <button className="App-button" onClick={() => window.location.href = 'http://localhost:5173'}>
                            <span className="title-text">Pr      </span>
                            <img src={Finallogo} className="App-logo" alt="logo" />
                            <span className="title-text">  Tube</span>
                        </button>
                        <div className="button-container">
                            <AuthButtons/>
                        </div>
                    </header>
                    <Routes>
                        {/* Main video list route */}
                        <Route path="/" element={<VideoList/>}/>
                        {/* Video player route with dynamic ID */}
                        <Route path="/videos/:id" element={<VideoPlayer/>}/>
                    </Routes>
                </div>
            </Router>
        </Auth0Provider>
    );
}

export default App;
