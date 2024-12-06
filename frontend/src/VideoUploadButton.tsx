import { useNavigate } from 'react-router-dom';
import './VideoUploadButton.css';
import React from "react"; // Import the CSS file
import IchigoFinal from "./assets/Ichigo_final_Upload-ezgif.com-gif-maker.svg";

const UploadButton = () => {
    const navigate = useNavigate();

    return (
        <button
            onClick={() => navigate('/upload')} // Navigate to the /upload route
            className="upload-button" // Use the CSS class for styling
        >
            <img src={IchigoFinal} className="ichigo-icon" alt="logo" />
        </button>
    );
};

export default UploadButton;

