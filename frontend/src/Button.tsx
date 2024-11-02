function nextPage() {
    window.location.href = 'VideoList.css';
}

const Button = () => {
    return (
        <div>
            <button onClick={() => nextPage()}>Skip</button>
        </div>
    );
}

export default Button;
