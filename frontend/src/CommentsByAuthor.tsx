import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

interface CommentDTO {
    text: string;
    author: string;
    videoId: number;
}

const CommentsByAuthor: React.FC = () => {
    const { author } = useParams<{ author: string }>();
    const [comments, setComments] = useState<CommentDTO[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [videoData, setVideoData] = useState<Map<number, { thumbnail: string }>>(new Map());

    useEffect(() => {
        const fetchCommentsByAuthor = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/videos/comments/author/${author}`);
                const contentType = response.headers.get('content-type');
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                if (contentType && contentType.indexOf('application/json') === -1) {
                    throw new Error('Response is not JSON');
                }
                const data: CommentDTO[] = await response.json();
                setComments(data);

                // Fetch video data to get thumbnails (one fetch per unique videoId)
                const uniqueVideoIds = Array.from(new Set(data.map(comment => comment.videoId)));
                for (const videoId of uniqueVideoIds) {
                    const videoResponse = await fetch(`http://localhost:8080/api/videos/${videoId}`);
                    const videoData = await videoResponse.json();
                    setVideoData(prevData => new Map(prevData).set(videoId, { thumbnail: videoData.imagePath }));
                }
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
                {comments.map((comment, index) => (
                    <li key={index} className="comment-item">
                        {/* Video info with thumbnail */}
                        <div className="video-info">
                            {videoData.has(comment.videoId) && (
                                <>
                                    <img
                                        src={videoData.get(comment.videoId)?.thumbnail}
                                        alt="Video Thumbnail"
                                        className="thumbnail"
                                    />
                                    {/* Video title is now removed as per request */}
                                </>
                            )}
                        </div>
                        {/* Comment text */}
                        <div className="comment-content">
                            <strong>{comment.author}</strong>: {comment.text}
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CommentsByAuthor;
