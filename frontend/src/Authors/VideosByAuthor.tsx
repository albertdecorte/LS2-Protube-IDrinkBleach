import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import VideosSection from './VideosSection'; // Assuming we have VideosSection.tsx
import './VideosByAuthor.css'; // Import your CSS

// VideoDTO interface definition
interface VideoDTO {
    id: number;
    title: string;
    imagePath?: string;    // Optional thumbnail path
    thumbnail?: string;    // Optional thumbnail (for flexibility)
    user: string;
}

const VideosByAuthor: React.FC = () => {
    // Get the author from the URL params
    const { author } = useParams<{ author: string }>();
    // State management for videos, loading, and error
    const [videos, setVideos] = useState<VideoDTO[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Fetch all videos by a specific author
        const fetchVideosByAuthor = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/videos/author/${author}/videos`);
                const contentType = response.headers.get('content-type');

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                if (contentType && contentType.indexOf('application/json') === -1) {
                    throw new Error('Response is not JSON');
                }

                const videosData: VideoDTO[] = await response.json();
                setVideos(videosData);
            } catch (err) {
                if (err instanceof Error) {
                    setError(err.message);
                } else {
                    setError('An unknown error occurred');
                }
                console.error('Error fetching videos by author:', err);
            } finally {
                setLoading(false);
            }
        };

        fetchVideosByAuthor();
    }, [author]); // Refetch if author changes

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="videos-by-author">
            <h1 style={{color:"white"}}>Videos by {author}</h1>
            <VideosSection videos={videos} />
        </div>
    );
};

export default VideosByAuthor;