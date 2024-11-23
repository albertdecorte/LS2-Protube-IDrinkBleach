import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
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
    videoTitle: string;
}

const VideoPlayer: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [video, setVideo] = useState<Video | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [expanded, setExpanded] = useState(false);
    const [recommendedVideos, setRecommendedVideos] = useState<Video[]>([]);
    const [comments, setComments] = useState<Comment[]>([]);

    useEffect(() => {
        // Fetch video details
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

    useEffect(() => {
        // Fetch recommended videos based on categories
        if (video) {
            fetch(`http://localhost:8080/api/videos`)
                .then(response => {
                    if (!response.ok) throw new Error('Failed to fetch videos');
                    return response.json();
                })
                .then(data => {
                    // Filter videos that belong to the same category
                    const sameCategoryVideos = data.filter((v: Video) =>
                        v.categories.some(category => video.categories.includes(category)) && v.id !== video.id
                    );

                    // If not enough videos in the same category, fill with random ones
                    const remainingVideos = data.filter((v: Video) => v.id !== video.id);
                    const randomVideos = remainingVideos
                        .sort(() => 0.5 - Math.random())
                        .slice(0, Math.max(10 - sameCategoryVideos.length, 0));

                    // Combine same-category videos and random ones, limit to 10
                    const recommendations = [...sameCategoryVideos, ...randomVideos].slice(0, 10);
                    setRecommendedVideos(recommendations);
                })
                .catch(error => {
                    console.error('Error fetching recommended videos:', error);
                });
        }
    }, [video]);

    useEffect(() => {
        // Fetch comments for the video
        fetch(`http://localhost:8080/api/videos/${id}/comments`)
            .then(response => {
                if (!response.ok) throw new Error('Failed to fetch comments');
                return response.json();
            })
            .then(data => {
                setComments(data);
            })
            .catch(error => {
                console.error('Error fetching comments:', error);
            });
    }, [id]);

    if (loading) return <p>Loading video...</p>;
    if (error) return <p>Error loading video: {error}</p>;
    if (!video) return <p>Video not found.</p>;

    const handleToggleExpand = () => {
        setExpanded(!expanded);
    };

    return (
        <div id="video-player-page">
            {/* Main video player section */}
            <div id="video-player-container">
                <video controls>
                    <source src={video.videoPath} type="video/mp4" />
                </video>
                <h1 id="video-title">{video.title}</h1>
                <p id="video-user">By {video.user}</p>
                {/* Clickable Description Box */}
                <div id="description-box" className={expanded ? 'expanded' : ''} onClick={handleToggleExpand}>
                    <p className="video-description">
                        {expanded ? video.description : `${video.description.slice(0, 100)}...`}
                    </p>
                    {expanded && (
                        <>
                            <p className="tags">
                                <strong>Tags:</strong>{' '}
                                {video.tags.map((tag, index) => (
                                    <span key={index} className="tag">#{tag}</span>
                                ))}
                            </p>
                            <p className="categories">
                                <strong>Categories:</strong>{' '}
                                {video.categories.map((category, index) => (
                                    <span key={index} className="category">#{category}</span>
                                ))}
                            </p>
                        </>
                    )}
                    <span className="toggle-button">{expanded ? 'Show Less' : 'Show More'}</span>
                </div>
                {/* Comments section */}
                <div id="comments-section">
                    <h2>Comments</h2>
                    {comments.length === 0 ? (
                        <p>No comments yet. Be the first to comment!</p>
                    ) : (
                        <ul className="comments-list">
                            {comments.map((comment, index) => (
                                <li key={index} className="comment-item">
                                    <p><strong>{comment.author}</strong> {comment.text}</p>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>

            {/* Recommended videos section */}
            <div id="recommended-videos">
                <h2>Recommended Videos</h2>
                <ul className="recommended-list">
                    {recommendedVideos.map((vid) => (
                        <li key={vid.id} className="recommended-item">
                            <Link to={`/videos/${vid.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                                <img src={vid.imagePath} alt={vid.title} className="thumbnail" />
                                <div className="video-info">
                                    <span className="video-title">{vid.title}</span>
                                    <span className="video-user">By {vid.user}</span>
                                </div>
                            </Link>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default VideoPlayer;
