import React, { useState } from "react";
import axios from "axios";

function UploadPage() {
    const [file, setFile] = useState(null);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!file) {
            setError("Выберите файл для загрузки");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {
            const token = localStorage.getItem("accessToken");

            const response = await axios.post("http://localhost:8080/minio/upload?bucket=avatar", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                    Authorization: `Bearer ${token}`
                }
            });

            setMessage(`Файл успешно загружен: ${response.data}`);
            setError("");
        } catch (err) {
            console.error("Ошибка загрузки:", err);
            setError("Ошибка при загрузке файла");
            setMessage("");
        }
    };

    return (
        <div style={{ maxWidth: "500px", margin: "auto", padding: "20px" }}>
            <h2>Загрузить аватар</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Выберите файл:</label>
                    <input type="file" onChange={handleFileChange} />
                </div>
                {error && <p style={{ color: "red" }}>{error}</p>}
                {message && <p style={{ color: "green" }}>{message}</p>}
                <button type="submit">Загрузить</button>
            </form>
        </div>
    );
}

export default UploadPage;