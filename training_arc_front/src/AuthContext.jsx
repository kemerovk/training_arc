// src/context/AuthContext.jsx
import React, { createContext, useContext, useState, useEffect } from "react";
import axios from "axios";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [accessToken, setAccessToken] = useState(null);
    const [loading, setLoading] = useState(true);

    // Проверяем наличие токена и пользователя при загрузке
    useEffect(() => {
        const token = localStorage.getItem("accessToken");
        const storedUser = localStorage.getItem("user");

        if (token && storedUser) {
            setAccessToken(token);
            setUser(JSON.parse(storedUser));
        }

        setLoading(false);
    }, []);

    // Логин
    const login = async (loginData, password) => {
        try {
            const res = await axios.post("http://localhost:8080/credentials/login-jwt", {
                login: loginData,
                password,
            });

            const { access, refresh, user } = res.data;

            // Сохраняем данные
            localStorage.setItem("accessToken", access);
            localStorage.setItem("refreshToken", refresh);
            localStorage.setItem("user", JSON.stringify(user));

            setAccessToken(access);
            setUser(user);
            return true;
        } catch (error) {
            console.error("Ошибка входа:", error);
            logout();
            return false;
        }
    };

    // Выход
    const logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("user");

        setAccessToken(null);
        setUser(null);
    };

    return (
        <AuthContext.Provider
            value={{
                user,
                accessToken,
                isAuthenticated: !!user,
                loading,
                login,
                logout,
            }}
        >
            {!loading && children}
        </AuthContext.Provider>
    );
};

// Хук для использования контекста
export const useAuth = () => {
    return useContext(AuthContext);
};