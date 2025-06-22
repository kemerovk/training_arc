import React, { useState } from "react";
import axios from "axios";

function UploadPage() {
    const [file, setFile] = useState(null);
    const [uploading, setUploading] = useState(false);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const getNewAccessToken = async (refreshToken) => {
        try {
            const response = await axios.post("http://localhost:8080/credentials/refresh", {
                access: localStorage.getItem("accessToken"),
                refresh: localStorage.getItem("refreshToken")
            });

            const { access } = response.data;
            localStorage.setItem("accessToken", access);
            return access;
        } catch (err) {
            throw new Error("Не удалось обновить токен");
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!file) {
            setError("Пожалуйста, выберите файл для загрузки");
            return;
        }

        let token = localStorage.getItem("accessToken");

        if (!token) {
            setError("Токен авторизации отсутствует. Пожалуйста, войдите снова.");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        setUploading(true);
        setMessage("");
        setError("");

        try {
            // Первая попытка загрузки
            const response = await sendUploadRequest(formData, token);
            setMessage(`Файл "${file.name}" успешно загружен. Ответ от сервера: ${response.data}`);
        } catch (err) {
            console.error("Ошибка загрузки:", err);

            // Если ошибка 401 — пробуем обновить токен
            if (err.response?.status === 401) {
                try {
                    const newToken = await getNewAccessToken();
                    const retryResponse = await sendUploadRequest(formData, newToken);
                    setMessage(`Файл "${file.name}" успешно загружен (через новый токен). Ответ: ${retryResponse.data}`);
                } catch (refreshError) {
                    setError("Сессия истекла. Пожалуйста, войдите снова.");
                }
            } else if (err.request) {
                setError("Нет ответа от сервера. Убедитесь, что он запущен и доступен.");
            } else {
                setError("Ошибка при отправке запроса: " + err.message);
            }
        } finally {
            setUploading(false);
        }
    };

    const sendUploadRequest = async (formData, token) => {
        return axios.post("http://localhost:8080/minio/upload", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
                Authorization: `Bearer ${token}`,
            },
        });
    };

    return (
        <div style={{ maxWidth: "500px", margin: "auto", padding: "20px" }}>
            <h2>Загрузить файл в MinIO</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Выберите файл:</label>
                    <input type="file" onChange={handleFileChange} />
                </div>
                {error && <p style={{ color: "red" }}>{error}</p>}
                {message && <p style={{ color: "green" }}>{message}</p>}
                <button type="submit" disabled={uploading}>
                    {uploading ? "Загрузка..." : "Загрузить"}
                </button>
            </form>
        </div>
    );
}

export default UploadPage;