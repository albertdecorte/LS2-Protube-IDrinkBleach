import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import CommentsSection from './CommentsSection';

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
        <div>
            <h1>Comments by {author}</h1>
            <CommentsSection comments={comments} />
        </div>
    );
};

export default CommentsByAuthor;