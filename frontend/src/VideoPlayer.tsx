import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
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

const VideoPlayer: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [video, setVideo] = useState<Video | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [expanded, setExpanded] = useState(false);

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

    const handleToggleExpand = () => {
        setExpanded(!expanded);
    };

    return (
        <div id="video-player-container">
            <video controls>
                <source src={video.videoPath} type="video/mp4"/>
            </video>
            <h1 id="video-title">{video.title}</h1>
            <p id="video-user">{video.user}</p>
            {/* Clickable Description Box */}
            <div id="description-box" className={expanded ? 'expanded' : ''} onClick={handleToggleExpand}>
                <p className="video-description">
                    {expanded ? video.description : `${video.description.slice(0, 100)}...`}
                </p>
                {expanded && (
                    <>
                        <p className="tags">
                            <strong>Tags:</strong> {video.tags.map((tag, index) => (
                            <span key={index} className="tag">#{tag}</span>
                        ))}
                        </p>
                        <p className="categories">
                            <strong>Categories:</strong> {video.categories.map((category, index) => (
                            <span key={index} className="category">#{category}</span>
                        ))}
                        </p>
                    </>
                )}
                <span className="toggle-button">{expanded ? 'Show Less' : 'Show More'}</span>
            </div>
        </div>
    );
};

export default VideoPlayer;
