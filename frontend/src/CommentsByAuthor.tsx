import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import CommentsSection from './CommentsSection';
import './CommentsByAuthor.css'; // Import the CSS

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

    useEffect(() => {
        const fetchCommentsByAuthor = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/videos/comments/author/${author}`);
                const data: CommentDTO[] = await response.json();
                setComments(data);
            } catch (err) {
                setError('Failed to load comments.');
            } finally {
                setLoading(false);
            }
        };
        fetchCommentsByAuthor();
    }, [author]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="comments-by-author">
            <h1>Comments by {author}</h1>
            <CommentsSection comments={comments} />
        </div>
    );
};

export default CommentsByAuthor;
