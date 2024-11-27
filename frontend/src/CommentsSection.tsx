import React from 'react';
import { Link } from 'react-router-dom';
import './CommentsByAuthor.css'; // Assuming you have a CSS file for styling

interface CommentDTO {
    text: string;
    author: string;
    videoId: number;
    videoTitle: string;
    videoThumbnail: string;
}

interface CommentsSectionProps {
    comments: CommentDTO[];
}

const CommentsSection: React.FC<CommentsSectionProps> = ({ comments }) => {
    return (
        <div className="comments-section">
            <ul className="comments-list">
                {comments.map((comment, index) => (
                    <li key={index} className="comment-item">
                        {/* Video Thumbnail and Title */}
                        <Link to={`/videos/${comment.videoId}`} className="video-info">
                            <img src={comment.videoThumbnail} alt={comment.videoTitle} className="thumbnail" />
                            <div className="video-title">{comment.videoTitle}</div>
                        </Link>
                        {/* Comment Text */}
                        <div className="comment-content">
                            <p>{comment.text}</p>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CommentsSection;
