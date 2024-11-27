import React from 'react';
import { Link } from 'react-router-dom';
import './VideosByAuthor.css'; // Assuming you've updated to BEM

interface VideoDTO {
    id: number;
    title: string;
    thumbnail: string;
    user: string; // Author of the video
}

interface VideosSectionProps {
    videos: VideoDTO[];
}

const VideosSection: React.FC<VideosSectionProps> = ({ videos }) => {
    return (
        <div className="videos-section">
            <ul className="videos-section__list">
                {videos.map((video) => (
                    <li key={video.id} className="videos-section__item">
                        {/* Video Thumbnail */}
                        <Link to={`/videos/${video.id}`} className="videos-section__info">
                            <img
                                src={video.thumbnail}
                                alt={video.title}
                                className="videos-section__thumbnail"
                            />
                        </Link>

                        {/* Video Title and Author */}
                        <div className="videos-section__details">
                            <Link to={`/videos/${video.id}`} className="videos-section__title">
                                {video.title}
                            </Link>
                            <p className="videos-section__author">By: {video.user}</p>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default VideosSection;
