import React, { useEffect, useState } from 'react';

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

    return (
        <div>
            <h1>Video List</h1>
            {error && <p>Error: {error}</p>}
            <ul>
                {videos.map(video => (
                    <li key={video.id}>
                        {/* Thumbnail Image */}
                        <img
                            src={video.imagePath}
                            alt={video.title}
                            style={{ width: '100px', height: '100px' }}
                        />
                        {/* Title and User Information */}
                        <span>{`${video.title} by ${video.user}`}</span>
                        {/* Video Link */}
                        <a href={video.path || '#'} target="_blank" rel="noopener noreferrer">
                            Watch Video
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default VideoList;

