// src/components/UserHeader.jsx
import React from "react";
import { useAuth } from "./AuthContext";

const UserHeader = () => {
    const { user, logout } = useAuth();

    return (
        <div style={styles.header}>
            <div style={styles.userInfo}>
                <div style={styles.avatar}>
                    {user?.login?.charAt(0).toUpperCase() || "?"}
                </div>
                <div style={styles.textInfo}>
                    <strong>{user?.login || "Пользователь"}</strong>
                    <div>
                        <button onClick={logout} style={styles.logoutButton}>
                            Выйти
                        </button>
                    </div>
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
        cursor: "default"
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
    },
    logoutButton: {
        marginTop: "4px",
        padding: "4px 8px",
        fontSize: "12px",
        backgroundColor: "#dc3545",
        color: "white",
        border: "none",
        borderRadius: "4px",
        cursor: "pointer"
    }
};

export default UserHeader;