// VideoList.tsx
import React, { useEffect, useState } from 'react';
import './VideoList.css';

interface Video {
    id: number;
    title: string;
    user: string;
    path: string;
    imagePath: string;
}

const VideoList: React.FC = () => {
    const [videos, setVideos] = useState<Video[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/videos')
            .then(response => {
                if (!response.ok) throw new Error('Failed to fetch videos');
                return response.json();
            })
            .then(data => setVideos(data))
            .catch(error => setError(error.message));
    }, []);

    const goToVideoPlayer = (id: number) => {
        window.location.href = `/video-player.html?id=${id}`;
    };

    return (
        <div className="video-list-container">
            <h1>Video List</h1>
            {error && <p>Error: {error}</p>}
            <ul className="video-grid">
                {videos.map(video => (
                    <li
                        key={video.id}
                        className="video-item"
                        onClick={() => goToVideoPlayer(video.id)}
                        style={{ cursor: 'pointer' }}  // Make items look clickable
                    >
                        <img src={video.imagePath} alt={video.title} className="thumbnail" />
                        <div className="video-info">
                            <span className="video-title">{video.title}</span>
                            <span className="video-user">{video.user}</span>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default VideoList;
