// src/App.jsx
import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./LoginPage";
import DashboardPage from "./DashboardPage";
import RegisterPage from "./RegisterPage";
import UploadPage from "./UploadPage";
import UserHeader from "./UserHeader";

// Защита приватных маршрутов
const PrivateRoute = ({ children }) => {
    const token = localStorage.getItem("accessToken");
    if (!token) return <Navigate to="/login" />;
    return (
        <>
            <UserHeader />
            {children}
        </>
    );
};

// Защита публичных маршрутов
const PublicRoute = ({ children }) => {
    const token = localStorage.getItem("accessToken");
    if (token) return <Navigate to="/dashboard" />;
    return children;
};

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={<PublicRoute><LoginPage /></PublicRoute>} />
                <Route path="/register" element={<PublicRoute><RegisterPage /></PublicRoute>} />
                <Route path="/dashboard" element={<PrivateRoute><DashboardPage /></PrivateRoute>} />
                <Route path="/upload" element={<PrivateRoute><UploadPage /></PrivateRoute>} />
            </Routes>
        </Router>
    );
}

export default App;