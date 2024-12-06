import React, { useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import './VideoUpload.css';

const UploadVideo: React.FC = () => {
    const { isAuthenticated, user } = useAuth0();
    const [newTitle, setNewTitle] = useState<string>('');
    const [videoFilePath, setVideoFilePath] = useState<File | null>(null);
    const [thumbnailFilePath, setThumbnailFilePath] = useState<File | null>(null); // Thumbnail
    const [videoDescription, setVideoDescription] = useState<string>('');
    const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
    const [selectedTags, setSelectedTags] = useState<string[]>([]);
    const [buttonText, setButtonText] = useState<string>('Pujar Vídeo'); // State for button text

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
        if (thumbnailFilePath) formData.append('thumbnailFile', thumbnailFilePath); // Adjuntar thumbnail si existeix
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
                setButtonText('Video Uploaded'); // Change button text on success
            })
            .catch(error => {
                console.error('Error:', error);
            });
    };

    return (
        <div className="video-upload-container">
            <h2 style={{ color: '#ffffff' }}>Video Upload</h2>
            <form className="video-upload-form" onSubmit={(e) => { e.preventDefault(); handleAddVideo(); }}>
                <div className="form-group">
                    <label>Títol:</label>
                    <input
                        type="text"
                        value={newTitle}
                        onChange={(e) => setNewTitle(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Descripció:</label>
                    <textarea
                        value={videoDescription}
                        onChange={(e) => setVideoDescription(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Categories:</label>
                    <input
                        type="text"
                        value={selectedCategories.join(', ')}
                        onChange={(e) => setSelectedCategories(e.target.value.split(',').map(item => item.trim()))}
                    />
                </div>
                <div className="form-group">
                    <label>Etiquetes:</label>
                    <input
                        type="text"
                        value={selectedTags.join(', ')}
                        onChange={(e) => setSelectedTags(e.target.value.split(',').map(item => item.trim()))}
                    />
                </div>
                <div className="form-group">
                    <label>Selecciona el vídeo:</label>
                    <input
                        type="file"
                        onChange={(e) => setVideoFilePath(e.target.files ? e.target.files[0] : null)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Selecciona el thumbnail (opcional):</label>
                    <input
                        type="file"
                        onChange={(e) => setThumbnailFilePath(e.target.files ? e.target.files[0] : null)}
                    />
                </div>
                <button type="submit"
                        onClick={() => window.location.href = 'http://localhost:5173'}>{buttonText}</button>
            </form>
        </div>
    );
};

export default UploadVideo;
