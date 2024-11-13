import './App.css';
import {Auth0Provider, useAuth0} from '@auth0/auth0-react';
import Finallogo from './assets/Finallogo.svg';
import VideoList from "./VideoList";
import VideoPlayer from './VideoPlayer';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

const domain = "dev-r7hj507hsi3jn34i.us.auth0.com";
const clientId = "g84SYUoiDvFIGevVYEBH5AcB4xaoHUFZ";

// Component to show login/logout buttons
const AuthButtons = () => {
    const {loginWithRedirect, logout, isAuthenticated} = useAuth0();
    return (
        <div>
            {!isAuthenticated && (
                <button onClick={() => loginWithRedirect()}>Login</button>
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
                        <h1 className="App-title">
                            <span className="title-text">Pr  </span>
                            <img src={Finallogo} className="App-logo" alt="logo"/>
                            <span className="title-text">     Tube</span>
                        </h1>
                        <div className="button-container">
                            <AuthButtons/>
                            <div className="diagonal-line"></div>
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
