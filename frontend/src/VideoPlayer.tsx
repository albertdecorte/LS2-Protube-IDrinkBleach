import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

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
        // Fetch video data by ID from the backend
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
        <div>
            <h1>{video.title}</h1>
            <p>{video.user}</p>
            <video controls src={video.path} width="600"></video>
        </div>
    );
};

export default VideoPlayer;
