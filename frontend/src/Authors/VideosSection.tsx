import React from 'react';
import { Link } from 'react-router-dom';
//import "/frontend/src/Authors/VideoSection.css" // Assuming you have a CSS file for styling

interface VideoDTO {
    id: number;
    title: string;
    imagePath?: string;    // Optional thumbnail path
    thumbnail?: string;    // Optional thumbnail (for flexibility)
    user: string;
}

interface VideosSectionProps {
    videos: VideoDTO[]; // List of videos passed to the section
}

const VideosSection: React.FC<VideosSectionProps> = ({ videos }) => {
    return (
        <div className="videos-section">
            <ul className="videos-list">
                {videos.map((video) => (
                    <li key={video.id} className="video-item">
                        <Link to={`/videos/${video.id}`} className="video-info">
                            {/* Use `imagePath` if available, or fall back to `thumbnail` */}
                            <img
                                src={video.imagePath || video.thumbnail || '/default-thumbnail.jpg'}
                                alt={video.title}
                                className="thumbnail"
                            />
                            <div className="video-title">{video.title}</div>
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default VideosSection;
