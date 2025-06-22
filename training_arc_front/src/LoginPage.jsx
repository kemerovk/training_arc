import React, { useState } from "react";
import axios from "axios";

function LoginPage() {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post("http://localhost:8080/credentials/login-jwt", {
                login,
                password
            });

            const { access, refresh } = response.data;

            // Сохраняем токены в localStorage
            localStorage.setItem("accessToken", access);
            localStorage.setItem("refreshToken", refresh);

            // Можно сделать редирект или обновление состояния
            window.location.href = "/dashboard"; // или что-то другое
        } catch (err) {
            setError("Неверный логин или пароль");
        }
    };

    return (
        <div style={{ maxWidth: "400px", margin: "auto" }}>
            <h2>Вход</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Логин</label>
                    <input
                        type="text"
                        value={login}
                        onChange={(e) => setLogin(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Пароль</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p style={{ color: "red" }}>{error}</p>}
                <button type="submit">Войти</button>
            </form>
        </div>
    );
}

export default LoginPage;
