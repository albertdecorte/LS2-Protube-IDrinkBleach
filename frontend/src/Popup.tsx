import React from 'react';
import './Popup.css';

interface PopupProps {
    isOpen: boolean;
    onClose: () => void;
    onNavigate: (path: string) => void;
}

const Popup: React.FC<PopupProps> = ({ isOpen, onClose, onNavigate }) => {
    if (!isOpen) return null;

    return (
        <div className="popup-overlay" onClick={onClose}>
            <div className="popup-content" onClick={(e) => e.stopPropagation()}>
                <h2>Navigate to:</h2>
                <button onClick={() => onNavigate('/author')}>Author</button>
                <button onClick={() => onNavigate('/authors-of-videos')}>Videos by Author</button>
                <button onClick={onClose}>Close</button>
            </div>
        </div>
    );
};

export default Popup;