import React from 'react';

interface CommentDTO {
    text: string;
    author: string;
    videoTitle?: string; // Optional if not needed in this component
}

interface CommentsSectionProps {
    comments: CommentDTO[];
}

const CommentsSection: React.FC<CommentsSectionProps> = ({ comments }) => {
    return (
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
    );
};

export default CommentsSection;
