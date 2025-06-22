import React, { useState } from "react";
import axios from "axios";

function RegisterPage() {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [age, setAge] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await axios.post("http://localhost:8080/credentials/register", {
                login,
                password,
                email,
                age,
            });

            setSuccess("Регистрация успешна! Перенаправление на вход...");
            setTimeout(() => {
                window.location.href = "/login"; // перенаправляем на страницу входа
            }, 2000);
        } catch (err) {
            setError("Ошибка регистрации. Проверьте данные или попробуйте позже.");
        }
    };

    return (
        <div style={{ maxWidth: "400px", margin: "auto" }}>
            <h2>Регистрация</h2>
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
                <div>
                    <label>Email</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Возраст</label>
                    <input
                        type="number"
                        value={age}
                        onChange={(e) => setAge(e.target.value)}
                        required
                    />
                </div>
                {error && <p style={{ color: "red" }}>{error}</p>}
                {success && <p style={{ color: "green" }}>{success}</p>}
                <button type="submit">Зарегистрироваться</button>
            </form>
            <p style={{ marginTop: "15px" }}>
                Уже есть аккаунт? <a href="/login">Войдите</a>
            </p>
        </div>
    );
}

export default RegisterPage;