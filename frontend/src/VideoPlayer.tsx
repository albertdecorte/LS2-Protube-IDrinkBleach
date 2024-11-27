import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import './VideoPlayer.css';

interface Video {
    id: number;
    title: string;
    user: string;
    videoPath: string;
    imagePath: string;
    description: string;
    tags: string[];
    categories: string[];
}

interface Comment {
    text: string;
    author: string;
    videoId: number;
}

const VideoPlayer: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const { isAuthenticated, user } = useAuth0(); // Obtenim l'usuari autenticat
    const [video, setVideo] = useState<Video | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [expanded, setExpanded] = useState(false);
    const [recommendedVideos, setRecommendedVideos] = useState<Video[]>([]);
    const [comments, setComments] = useState<Comment[]>([]);
    const [visibleVideos, setVisibleVideos] = useState(0);
    const [newComment, setNewComment] = useState("");
    const navigate = useNavigate();

    // Calcular els vídeos visibles basant-se en l'altura de la pantalla
    useEffect(() => {
        const updateVisibleVideos = () => {
            const videoItemHeight = 150; // Alçada estimada per vídeo recomanat
            const screenHeight = window.innerHeight; // Alçada de la finestra
            setVisibleVideos(Math.floor(screenHeight / videoItemHeight)); // Càlcul
        };

        updateVisibleVideos();
        window.addEventListener('resize', updateVisibleVideos);
        return () => {
            window.removeEventListener('resize', updateVisibleVideos);
        };
    }, []);

    useEffect(() => {
        // Recuperar els detalls del vídeo
        setLoading(true);
        fetch(`http://localhost:8080/api/videos/${id}`)
            .then(response => {
                if (!response.ok) throw new Error('Error recuperant el vídeo');
                return response.json();
            })
            .then(data => {
                setVideo(data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error carregant el vídeo:', error);
                setError(error.message);
                setLoading(false);
            });
    }, [id]);

    useEffect(() => {
        // Recuperar els comentaris del vídeo
        fetch(`http://localhost:8080/api/videos/${id}/comments`)
            .then(response => {
                if (!response.ok) throw new Error('Error recuperant els comentaris');
                return response.json();
            })
            .then(data => {
                setComments(data);
            })
            .catch(error => {
                console.error('Error recuperant comentaris:', error);
            });
    }, [id]);

    useEffect(() => {
        // Recuperar vídeos recomanats basats en categories
        if (video) {
            fetch(`http://localhost:8080/api/videos`)
                .then(response => {
                    if (!response.ok) throw new Error('Error recuperant vídeos');
                    return response.json();
                })
                .then(data => {
                    const sameCategoryVideos = data.filter((v: Video) =>
                        v.categories.some(category => video.categories.includes(category)) && v.id !== video.id
                    );
                    const remainingVideos = data.filter(
                        (v: Video) => v.id !== video.id && !sameCategoryVideos.includes(v)
                    );
                    const randomVideos = remainingVideos.sort(() => 0.5 - Math.random());
                    const allVideos = [...sameCategoryVideos, ...randomVideos].filter(
                        (v, index, self) => self.findIndex(vid => vid.id === v.id) === index
                    );
                    setRecommendedVideos(allVideos.slice(0, visibleVideos));
                })
                .catch(error => {
                    console.error('Error recuperant vídeos recomanats:', error);
                });
        }
    }, [video, visibleVideos]);

    if (loading) return <p>Carregant vídeo...</p>;
    if (error) return <p>Error carregant vídeo: {error}</p>;
    if (!video) return <p>Vídeo no trobat.</p>;

    const handleToggleExpand = () => {
        setExpanded(!expanded);
    };

    const handleRecommendedVideoClick = (videoId: number) => {
        navigate(`/videos/${videoId}`);
    };

    const handleAddComment = () => {
        if (!isAuthenticated) {
            alert("Has d'estar autenticat per afegir comentaris.");
            return;
        }

        const comment = {
            text: newComment,
            author: user?.name || "Usuari desconegut", // Utilitzem el nom de l'usuari autenticat
            videoId: parseInt(id!)
        };

        fetch(`http://localhost:8080/api/videos/${id}/comments`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(comment)
        })
            .then((response) => {
                if (!response.ok) throw new Error("No s'ha pogut afegir el comentari");
                return response.json();
            })
            .then((newComment) => {
                setComments((prevComments) => [...prevComments, newComment]);
                setNewComment(""); // Resetejar el formulari
            })
            .catch((error) => console.error("Error:", error));
    };

    return (
        <div id="video-player-page">
            <div id="video-player-container">
                <video controls>
                    <source src={video.videoPath} type="video/mp4" />
                </video>
                <h1 id="video-title">{video.title}</h1>
                <p id="video-user">Per {video.user}</p>
                <div id="description-box" className={expanded ? 'expanded' : ''} onClick={handleToggleExpand}>
                    <p className="video-description">
                        {expanded ? video.description : `${video.description.slice(0, 100)}...`}
                    </p>
                    {expanded && (
                        <>
                            <p className="tags">
                                <strong>Tags:</strong>{" "}
                                {video.tags.map((tag, index) => (
                                    <span key={index} className="tag">#{tag}</span>
                                ))}
                            </p>
                            <p className="categories">
                                <strong>Categories:</strong>{" "}
                                {video.categories.map((category, index) => (
                                    <span key={index} className="category">#{category}</span>
                                ))}
                            </p>
                        </>
                    )}
                    <span className="toggle-button">{expanded ? 'Mostra menys' : 'Mostra més'}</span>
                </div>
                <div id="comments-section">
                    <h2>Comentaris</h2>
                    {comments.length === 0 ? (
                        <p>No hi ha comentaris encara. Sigues el primer!</p>
                    ) : (
                        <ul className="comments-list">
                            {comments.map((comment, index) => (
                                <li key={index} className="comment-item">
                                    <p><strong>{comment.author}</strong> {comment.text}</p>
                                </li>
                            ))}
                        </ul>
                    )}
                    <form
                        onSubmit={(e) => {
                            e.preventDefault();
                            handleAddComment();
                        }}
                    >
                        <textarea
                            placeholder="Escriu el teu comentari..."
                            value={newComment}
                            onChange={(e) => setNewComment(e.target.value)}
                        />
                        <button type="submit">Enviar</button>
                    </form>
                </div>
            </div>
            <div id="recommended-videos">
                <h2>Vídeos recomanats</h2>
                <ul className="recommended-list">
                    {recommendedVideos.map((vid) => (
                        <li
                            key={vid.id}
                            className="recommended-item"
                            onClick={() => handleRecommendedVideoClick(vid.id)}
                        >
                            <img src={vid.imagePath} alt={vid.title} className="thumbnail" />
                            <div className="video-info">
                                <span className="video-title">{vid.title}</span>
                                <span className="video-user">Per {vid.user}</span>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default VideoPlayer;
