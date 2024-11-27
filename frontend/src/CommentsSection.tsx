import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import './CommentsByAuthor.css';

interface CommentDTO {
    text: string;
    author: string;
    videoId: number;
}

interface VideoDTO {
    id: number;
    title: string;
    imagePath: string;
}

interface CommentWithVideo extends CommentDTO {
    videoTitle: string;
    videoThumbnail: string;
}

const CommentsByAuthor: React.FC = () => {
    const { author } = useParams<{ author: string }>();
    const [commentsWithVideo, setCommentsWithVideo] = useState<CommentWithVideo[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchCommentsByAuthor = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/videos/comments/author/${author}`);
                if (!response.ok) throw new Error('Network response was not ok');
                const comments: CommentDTO[] = await response.json();

                // Fetch video details for each comment's videoId
                const fetchVideoDetails = async (videoId: number) => {
                    const videoResponse = await fetch(`http://localhost:8080/api/videos/${videoId}`);
                    if (!videoResponse.ok) throw new Error('Failed to fetch video details');
                    const videoData: VideoDTO = await videoResponse.json();
                    return videoData;
                };

                // Fetch video details for all comments and merge with comments
                const commentsWithDetails = await Promise.all(
                    comments.map(async (comment) => {
                        const videoData = await fetchVideoDetails(comment.videoId);
                        return {
                            ...comment,
                            videoTitle: videoData.title,
                            videoThumbnail: videoData.imagePath,
                        };
                    })
                );

                setCommentsWithVideo(commentsWithDetails);
            } catch (err) {
                if (err instanceof Error) {
                    setError(err.message);
                } else {
                    setError('An unknown error occurred');
                }
                console.error('Error fetching comments by author:', err);
            } finally {
                setLoading(false);
            }
        };

        fetchCommentsByAuthor();
    }, [author]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="comments-by-author">
            <h1>Comments by {author}</h1>
            <ul className="comments-list">
                {commentsWithVideo.map((comment, index) => (
                    <li key={index} className="comment-item">
                        {/* Video Thumbnail and Title */}
                        <Link to={`/videos/${comment.videoId}`} className="video-info">
                            <img src={comment.videoThumbnail} alt={comment.videoTitle} className="thumbnail" />
                            <div className="video-title">{comment.videoTitle}</div>
                        </Link>
                        {/* Comment Text */}
                        <div className="comment-content">
                            <p><strong>{comment.author}:</strong> {comment.text}</p>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CommentsByAuthor;
