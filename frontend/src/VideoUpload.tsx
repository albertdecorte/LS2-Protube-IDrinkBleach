import React, { useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { useNavigate } from 'react-router-dom';
import './VideoUpload.css';
const VideoUpload: React.FC = () => {
    const { isAuthenticated, user } = useAuth0();
    const navigate = useNavigate();
    const [newTitle, setNewTitle] = useState<string>('');
    const [videoFilePath, setVideoFilePath] = useState<File | null>(null);
    const [thumbnailFilePath, setThumbnailFilePath] = useState<File | null>(null);
    const [videoDescription, setVideoDescription] = useState<string>('');
    const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
    const [selectedTags, setSelectedTags] = useState<string[]>([]);

    const handleAddVideo = () => {
        if (!isAuthenticated) {
            alert("Has d'estar autenticat per pujar un vídeo.");
            return;
        }

        if (!videoFilePath) {
            alert("Has de seleccionar un vídeo.");
            return;
        }

        const formData = new FormData();
        formData.append('videoFile', videoFilePath);
        if (thumbnailFilePath) formData.append('thumbnailFile', thumbnailFilePath);
        formData.append('title', newTitle);
        formData.append('description', videoDescription);
        formData.append('categories', selectedCategories.join(', '));
        formData.append('tags', selectedTags.join(', '));
        formData.append('user', user?.name || "Usuari desconegut");

        fetch('http://localhost:8080/api/videos/upload', {
            method: 'POST',
            body: formData,
        })
            .then(response => {
                if (!response.ok) throw new Error('Error en pujar el vídeo');
                return response.json();
            })
            .then(data => {
                console.log('Vídeo pujat correctament:', data);
                navigate('/'); // Redirigeix a la pàgina d'inici després de pujar el vídeo
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    return (
        <div>
            <h1 style={{color:"white"}}>Upload video</h1>
            <form onSubmit={(e) => { e.preventDefault(); handleAddVideo(); }}>
                <div>
                    <label>Title:</label>
                    <input
                        type="text"
                        value={newTitle}
                        onChange={(e) => setNewTitle(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Description:</label>
                    <textarea
                        value={videoDescription}
                        onChange={(e) => setVideoDescription(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Categories:</label>
                    <input
                        type="text"
                        value={selectedCategories.join(', ')}
                        onChange={(e) => setSelectedCategories(e.target.value.split(',').map(item => item.trim()))}
                    />
                </div>
                <div>
                    <label>Tags:</label>
                    <input
                        type="text"
                        value={selectedTags.join(', ')}
                        onChange={(e) => setSelectedTags(e.target.value.split(',').map(item => item.trim()))}
                    />
                </div>
                <div>
                    <label>Select video:</label>
                    <input
                        style={{color:"white"}}
                        type="file"
                        onChange={(e) => setVideoFilePath(e.target.files ? e.target.files[0] : null)}
                        required
                    />
                </div>
                <div>
                    <label>Select thumbnail (optional):</label>
                    <input
                        style={{color:"white"}}
                        type="file"
                        onChange={(e) => setThumbnailFilePath(e.target.files ? e.target.files[0] : null)}
                    />
                </div>
                <button type="submit">Upload video</button>
            </form>
        </div>
    );
};

export default VideoUpload;
