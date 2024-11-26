import React, { useState, useEffect } from 'react';

// Define the component
const AuthorButtons: React.FC = () => {
    // State to hold the authors list
    const [authors, setAuthors] = useState<string[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    // Fetch authors data from the API on component mount
    useEffect(() => {
        const fetchAuthors = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/videos/comments/authors');
                if (!response.ok) {
                    throw new Error('Failed to fetch authors');
                }
                const data: string[] = await response.json();
                setAuthors(data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An error occurred');
            } finally {
                setLoading(false);
            }
        };

        fetchAuthors();
    }, []); // Empty dependency array means this effect runs only once when the component mounts

    // Handle loading state
    if (loading) {
        return <div>Loading authors...</div>;
    }

    // Handle error state
    if (error) {
        return <div>Error: {error}</div>;
    }

    // Render buttons for each author
    return (
        <div className="authors-container">
            <ul className="authors-list">
                {authors.map((author, index) => (
                    <li key={index} className="author-item">
                        <button className="author-button">
                            {author}
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AuthorButtons;