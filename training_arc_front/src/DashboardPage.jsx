import React, { useEffect, useState } from "react";
import api from "./api/axiosInstance.js";

function DashboardPage() {
    const [client, setClient] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchClient = async () => {
            try {
                const response = await api.get("http://localhost:8080/clients/me");
                setClient(response.data);
            } catch (err) {
                setError("Не удалось загрузить данные пользователя");
            }
        };

        fetchClient();
    }, []);

    if (error) {
        return <p style={{ color: "red" }}>{error}</p>;
    }

    if (!client) {
        return <p>Загрузка...</p>;
    }

    return (
        <div style={{ maxWidth: "600px", margin: "auto" }}>
            <h2>Добро пожаловать, {client.login}!</h2>
            <ul>
                <li><strong>ID:</strong> {client.id}</li>
                <li><strong>Email:</strong> {client.email}</li>
                <li><strong>Возраст:</strong> {client.age}</li>
                <li><strong>MinIO путь:</strong> {client.minioPath}</li>
                <li><strong>Дата создания:</strong> {client.creationTime}</li>
            </ul>
        </div>
    );
}

export default DashboardPage;
