import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './VideoPlayer.css';

interface Video {
    id: number;
    title: string;
    user: string;
    path: string;
    imagePath: string;
}

const VideoPlayer: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [video, setVideo] = useState<Video | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch(`http://localhost:8080/api/videos/${id}`)
            .then(response => {
                if (!response.ok) throw new Error('Failed to fetch video');
                return response.json();
            })
            .then(data => {
                setVideo(data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching video:', error);
                setError(error.message);
                setLoading(false);
            });
    }, [id]);

    if (loading) return <p>Loading video...</p>;
    if (error) return <p>Error loading video: {error}</p>;
    if (!video) return <p>Video not found.</p>;

    return (
        <div id="video-player-container">
            <h1 id="video-title">{video.title}</h1>
            <p id="video-user">{video.user}</p>
            <video controls>
                <source src={video.path} type="video/mp4" />
                </video>
        </div>
    );
};

export default VideoPlayer;

