function nextPage() {
    window.location.href = 'http://localhost:8080/api/videos';
}

const Button = () => {
    return (
        <div>
            <button onClick={() => nextPage()}>Skip</button>
        </div>
    );
}

export default Button;
