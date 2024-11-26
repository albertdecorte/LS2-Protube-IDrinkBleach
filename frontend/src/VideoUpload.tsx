import React, { useState } from "react";
import axios from "axios";
import "./VideoUpload.css";

const VideoUpload = () => {
    // State to store form data
    const [title, setTitle] = useState<string>("");
    const [videoPath, setVideoPath] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [categories, setCategories] = useState<string[]>([]);
    const [tags, setTags] = useState<string[]>([]);
    const [isUploading, setIsUploading] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string>("");

    // Handle form submission
    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setIsUploading(true);
        setErrorMessage("");

        // Prepare the data to send to the backend
        const videoData = {
            title,
            videoPath,
            description,
            categories,
            tags,
        };

        try {
            const response = await axios.post("http://localhost:8080/videos/upload", videoData);
            alert("Video uploaded successfully!");
            console.log(response.data); // Log the saved video data
        } catch (error) {
            setErrorMessage("Error uploading video");
        } finally {
            setIsUploading(false);
        }
    };

    return (
        <div className="video-upload-container">
            <h2>Upload a New Video</h2>

            {errorMessage && <div className="error-message">{errorMessage}</div>}

            <form onSubmit={handleSubmit} className="video-upload-form">
                <div className="form-group">
                    <label htmlFor="title">Title</label>
                    <input
                        type="text"
                        id="title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="videoPath">Video File Path</label>
                    <input
                        type="text"
                        id="videoPath"
                        value={videoPath}
                        onChange={(e) => setVideoPath(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="description">Description (Optional)</label>
                    <textarea
                        id="description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    ></textarea>
                </div>

                <div className="form-group">
                    <label htmlFor="categories">Categories (Optional, comma separated)</label>
                    <input
                        type="text"
                        id="categories"
                        value={categories.join(", ")}
                        onChange={(e) => setCategories(e.target.value.split(",").map((item) => item.trim()))}
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="tags">Tags (Optional, comma separated)</label>
                    <input
                        type="text"
                        id="tags"
                        value={tags.join(", ")}
                        onChange={(e) => setTags(e.target.value.split(",").map((item) => item.trim()))}
                    />
                </div>

                <button type="submit" disabled={isUploading}>
                    {isUploading ? "Uploading..." : "Upload Video"}
                </button>
            </form>
        </div>
    );
};

export default VideoUpload;
