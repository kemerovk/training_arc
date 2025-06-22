import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./LoginPage";
import DashboardPage from "./DashboardPage";
import RegisterPage from "./RegisterPage.jsx";
import UploadPage from "./UploadPage.jsx";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/dashboard" element={<DashboardPage />} />
                <Route path="/register" element={<RegisterPage/>}/>
                <Route path="/upload" element={<UploadPage/>}/>
            </Routes>
        </Router>
    );
}

export default App;