// src/DashboardPage.jsx
import React, { useEffect, useState } from "react";
import api from "./api/axiosInstance.js";
import UserHeader from "./UserHeader"; // Импортируем

function DashboardPage() {
    const [client, setClient] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchClient = async () => {
            try {
                const response = await api.get("/clients/me");
                setClient(response.data);
                localStorage.setItem("user", JSON.stringify(response.data));
            } catch (err) {
                setError("Не удалось загрузить данные пользователя");
            }
        };
        fetchClient();
    }, []);

    if (error) return <p style={{ color: "red" }}>{error}</p>;
    if (!client) return <p>Загрузка...</p>;

    return (
        <>
            <UserHeader /> {/* Показываем шапку */}
            <div style={{ maxWidth: "600px", margin: "auto", marginTop: "80px" }}>
                <h2>Добро пожаловать, {client.login}!</h2>
                <ul>
                    <li><strong>ID:</strong> {client.id}</li>
                    <li><strong>Email:</strong> {client.email}</li>
                    <li><strong>Возраст:</strong> {client.age}</li>
                    <li><strong>MinIO путь:</strong> {client.minioPath}</li>
                    <li><strong>Дата создания:</strong> {new Date(client.creationTime).toLocaleString()}</li>
                </ul>
            </div>
        </>
    );
}

export default DashboardPage;