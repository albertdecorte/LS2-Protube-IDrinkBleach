import { useNavigate } from 'react-router-dom';
import './VideoUploadButton.css'; // Import the CSS file

const UploadButton = () => {
    const navigate = useNavigate();

    return (
        <button
            onClick={() => navigate('/upload')} // Navigate to the /upload route
            className="upload-button" // Use the CSS class for styling
        >
            Upload Video
        </button>
    );
};

export default UploadButton;

