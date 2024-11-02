// VideoPlayer.tsx
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './VideoPLayer.css';

interface Video {
    id: number;
    title: string;
    path: string;
    user: string;
}

const VideoPlayer: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [video, setVideo] = useState<Video | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch(`http://localhost:8080/api/videos/${id}`)
            .then(response => {
                if (!response.ok) throw new Error('Failed to fetch video');
                return response.json();
            })
            .then(data => setVideo(data))
            .catch(error => setError(error.message));
    }, [id]);

    if (error) {
        return <p>Error: {error}</p>;
    }

    if (!video) {
        return <p>Loading...</p>;
    }

    return (
        <div className="video-player-container">
            <h1>{video.title}</h1>
            <p>Uploaded by {video.user}</p>
            <video controls src={video.path} width="100%" />
        </div>
    );
};

export default VideoPlayer;
