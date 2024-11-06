import React from 'react';
import { useNavigate } from 'react-router-dom';

const Button: React.FC = () => {
    const navigate = useNavigate();

    const nextPage = () => {
        navigate('/video-list');
    };

    return (
        <div>
            <button onClick={nextPage}>Skip</button>
        </div>
    );
}

export default Button;