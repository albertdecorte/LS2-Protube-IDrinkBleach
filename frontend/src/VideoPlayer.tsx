import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
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
    videoId: number;
}

const VideoPlayer: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [video, setVideo] = useState<Video | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [expanded, setExpanded] = useState(false);
    const [recommendedVideos, setRecommendedVideos] = useState<Video[]>([]);
    const [comments, setComments] = useState<Comment[]>([]);
    const [visibleVideos, setVisibleVideos] = useState(0);
    const navigate = useNavigate(); // For programmatic navigation

    // Calculate visible videos based on screen height
    useEffect(() => {
        const updateVisibleVideos = () => {
            const videoItemHeight = 150; // Estimated height per recommended video item (in pixels)
            const screenHeight = window.innerHeight; // Get window height
            setVisibleVideos(Math.floor(screenHeight / videoItemHeight)); // Calculate number of videos
        };

        updateVisibleVideos();
        window.addEventListener('resize', updateVisibleVideos);
        return () => {
            window.removeEventListener('resize', updateVisibleVideos);
        };
    }, []);

    useEffect(() => {
        // Fetch video details
        setLoading(true); // Reset loading state
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
    }, [id]); // Refetch video whenever the ID changes

    useEffect(() => {
        // Fetch recommended videos based on categories
        if (video) {
            fetch(`http://localhost:8080/api/videos`)
                .then(response => {
                    if (!response.ok) throw new Error('Failed to fetch videos');
                    return response.json();
                })
                .then(data => {
                    // Filter videos by the same category
                    const sameCategoryVideos = data.filter((v: Video) =>
                        v.categories.some(category => video.categories.includes(category)) && v.id !== video.id
                    );

                    // Add random videos to fill the list
                    const remainingVideos = data.filter(
                        (v: Video) => v.id !== video.id && !sameCategoryVideos.includes(v)
                    );

                    const randomVideos = remainingVideos.sort(() => 0.5 - Math.random());

                    // Combine same-category videos and random ones, remove duplicates
                    const allVideos = [...sameCategoryVideos, ...randomVideos].filter(
                        (v, index, self) => self.findIndex(vid => vid.id === v.id) === index
                    );

                    // Limit to the number of visible videos
                    setRecommendedVideos(allVideos.slice(0, visibleVideos));
                })
                .catch(error => {
                    console.error('Error fetching recommended videos:', error);
                });
        }
    }, [video, visibleVideos]);

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

    const handleRecommendedVideoClick = (videoId: number) => {
        navigate(`/videos/${videoId}`); // Programmatic navigation to the selected video
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
                        <li
                            key={vid.id}
                            className="recommended-item"
                            onClick={() => handleRecommendedVideoClick(vid.id)}
                        >
                            <img src={vid.imagePath} alt={vid.title} className="thumbnail" />
                            <div className="video-info">
                                <span className="video-title">{vid.title}</span>
                                <span className="video-user">By {vid.user}</span>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default VideoPlayer;
