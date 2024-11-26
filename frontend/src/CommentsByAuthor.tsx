import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

interface CommentDTO {
    text: string;
    author: string;
    videoTitle: string;
}

const CommentsByAuthor: React.FC = () => {
    const { author } = useParams<{ author: string }>();
    const [comments, setComments] = useState<CommentDTO[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchCommentsByAuthor = async () => {
            try {
                const response = await fetch(`/api/videos/comments/${author}`);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setComments(data);
            } catch (error) {
                setError('Error fetching comments by author');
                console.error('Error fetching comments by author:', error);
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
        <div>
            <h1>Comments by {author}</h1>
            {comments.length > 0 ? (
                <ul>
                    {comments.map((comment, index) => (
                        <li key={index}>
                            <strong>{comment.videoTitle}</strong>: {comment.text}
                        </li>
                    ))}
                </ul>
            ) : (
                <div>No comments found for this author.</div>
            )}
        </div>
    );
};

export default CommentsByAuthor;