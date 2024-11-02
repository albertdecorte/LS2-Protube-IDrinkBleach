import React, { useEffect, useState } from 'react';
import './VideoList.css';

interface Video {
    id: number;
    title: string;
    user: string;
    path: string;
    imagePath: string;
    width?: number;
    height?: number;
    duration?: number;
    description?: string;
    categories?: string[];
    tags?: string[];
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
            .then(data => {
                // Store the full JSON data in the videos state
                setVideos(data);
            })
            .catch(error => setError(error.message));
    }, []);

    return (
        <div className="video-list-container">
            <h1>Video List</h1>
            {error && <p>Error: {error}</p>}
            <ul className="video-grid">
                {videos.map(video => (
                    <a href={video.path} key={video.id} target="_blank" rel="noopener noreferrer">
                        <li className="video-item">
                            <img
                                src={video.imagePath}
                                alt={video.title}
                                className="thumbnail"
                            />
                            <div className="video-info">
                                <span className="video-title">{video.title}</span>
                                <span className="video-user">{video.user}</span>
                            </div>
                        </li>
                    </a>
                ))}
            </ul>
        </div>
    );
};

export default VideoList;
