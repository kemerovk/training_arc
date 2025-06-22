// src/components/UserHeader.jsx
import React from "react";
import axios from "axios";

const UserHeader = () => {
    const user = JSON.parse(localStorage.getItem("user")) || {};
    const handleLogout = async () => {
        try {
            await axios.post("http://localhost:8080/logout", {}, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("accessToken")}`
                }
            });
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("user");
            window.location.href = "/login";
        } catch (error) {
            console.error("Ошибка выхода:", error);
            // Можно добавить уведомление об ошибке
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("user");
            window.location.href = "/login";
        }
    };

    return (
        <div style={styles.header}>
            <div style={styles.userInfo} onClick={handleLogout}>
                <div style={styles.avatar}>
                    {user.login?.charAt(0).toUpperCase() || "?"}
                        </div>
                        <div style={styles.textInfo}>
                    <strong>{user.login || "Пользователь"}</strong>
                </div>
            </div>
        </div>
);
};

const styles = {
    header: {
        position: "fixed",
            top: 0,
            right: 0,
            left: 0,
            padding: "10px 20px",
            backgroundColor: "#ffffff",
            borderBottom: "1px solid #ddd",
            display: "flex",
            justifyContent: "flex-end",
            zIndex: 1000,
            fontFamily: "Arial, sans-serif"
    },
    userInfo: {
        display: "flex",
            alignItems: "center",
            gap: "10px",
            cursor: "pointer"
    },
    avatar: {
        width: "36px",
            height: "36px",
            borderRadius: "50%",
            backgroundColor: "#007bff",
            color: "white",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            fontSize: "16px",
            fontWeight: "bold"
    },
    textInfo: {
        textAlign: "right"
    }
};

export default UserHeader;