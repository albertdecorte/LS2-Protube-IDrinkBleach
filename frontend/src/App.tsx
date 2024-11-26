import './App.css';
import { Auth0Provider, useAuth0 } from '@auth0/auth0-react';
import Finallogo from './assets/Finallogo.svg';
import IchigoAuthors from './assets/IchigoAuthors.svg';
import VideoList from './VideoList';
import VideoPlayer from './VideoPlayer';
import LoginButton from './assets/LoginButton.svg';
import axios from 'axios'; // Necessites afegir axios per fer peticions HTTP
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import AuthorsOfComments from "./AuthorsOfComments";
import CommentsByAuthor from "./CommentsByAuthor";
import VideoUpload from './VideoUpload'; // Import the VideoUpload component
import UploadButton from './VideoUploadButton'; // Import the UploadButton component


const domain = 'dev-r7hj507hsi3jn34i.us.auth0.com';
const clientId = 'g84SYUoiDvFIGevVYEBH5AcB4xaoHUFZ';
const apiUrl = 'http://localhost:8080/api/users'; // URL del backend per afegir l'usuari

// Component per mostrar el botó de login/logout
const AuthButtons = () => {
    const { loginWithRedirect, logout, isAuthenticated, user } = useAuth0();

    // Manejant el login i logout
    const handleLogin = async () => {
        try {
            await loginWithRedirect();
        } catch (error) {
            console.error("Error al iniciar sessió:", error);
        }
    };

    const handleLogout = () => {
        logout({ returnTo: window.location.origin } as any);
    };

    // Funció per afegir l'usuari a la base de dades si no existeix
    const addUserToDatabase = async () => {
        if (user) {
            const userData = {
                userNameDTO: user.name,
                gmailDTO: user.email,
                passwordDTO: '', // Si no tens un password, pots deixar-lo buit o gestionar-lo segons la teva lògica
            };

            try {
                // Comprovar si l'usuari ja existeix a la base de dades
                const response = await axios.get(`${apiUrl}/exists/${user.email}`);
                if (!response.data.exists) {
                    // Si no existeix, afegim l'usuari a la base de dades
                    await axios.post(apiUrl, userData);
                    console.log('Usuari afegit a la base de dades');
                }
            } catch (error) {
                console.error('Error al gestionar l\'usuari a la base de dades:', error);
            }
        }
    };

    // Quan l'usuari estigui autenticat, intentar afegir-lo a la base de dades
    if (isAuthenticated) {
        addUserToDatabase();
    }

    return (
        <div style={{ background: 'transparent', padding: 0 }}>
            {!isAuthenticated && (
                <button
                    onClick={handleLogin}
                    style={{
                        backgroundColor: 'transparent',
                        border: 'none',
                        padding: 0,
                        margin: 0
                    }}
                >
                    <img src={LoginButton} className="button-icon" alt="logo" />
                </button>
            )}
            {isAuthenticated && (
                <>
                    <button onClick={handleLogout}>
                        Logout
                    </button>
                    <p>Has iniciat sessió correctament!</p>
                </>
            )}
        </div>
    );
};

const AuthorButton = () => {
    const navigate = useNavigate();

    return (
        <button
            className= "Author-Button"
            onClick={() => navigate('/author')}style={{
            flex: 2,
            alignItems : "center",
            backgroundColor: 'transparent',
            border: 'none',
            color: 'white',
            fontSize: '1rem',
            cursor: 'pointer',
            padding: '1rem',
            borderRadius: '4px',
        }}>
            <img src={IchigoAuthors} className="ichigo-icon" alt="logo" />
        </button>
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
                        <button
                            className="App-button"
                            onClick={() => window.location.href = 'http://localhost:5173'}
                        >
                            <span className="title-text">Pr      </span>
                            <img src={Finallogo} className="App-logo" alt="logo" />
                            <span className="title-text">  Tube</span>
                        </button>
                        <div className="button-container">
                            <AuthorButton />
                            <UploadButton /> {/* Add the UploadButton here */}
                            <AuthButtons />
                        </div>
                    </header>
                    <Routes>
                        {/* Ruta principal de la llista de vídeos */}
                        <Route path="/" element={<VideoList />} />
                        {/* Ruta del reproductor de vídeos amb ID dinàmic */}
                        <Route path="/videos/:id" element={<VideoPlayer />} />
                        <Route path="/author" element={<AuthorsOfComments />} /> {/* Updated route to /author */}
                        <Route path="/comments/:author" element={<CommentsByAuthor />} />
                        <Route path="/upload" element={<VideoUpload />} /> {/* New route for video upload */}
                    </Routes>
                </div>
            </Router>
        </Auth0Provider>
    );
}

export default App;
