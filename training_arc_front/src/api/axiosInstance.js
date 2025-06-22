import axios from "axios";

// Создаём экземпляр axios
const api = axios.create({
    baseURL: "http://localhost:8080",
   // withCredentials: true,
});

// Добавляем access token в каждый запрос
api.interceptors.request.use(
    (config) => {
        const accessToken = localStorage.getItem("accessToken");
        if (accessToken) {
            config.headers["Authorization"] = `Bearer ${accessToken}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

// Обработка ответа: если access токен истёк — пробуем refresh
api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        // Если access токен истёк
        if (
            error.response?.status === 401 &&
            !originalRequest._retry
        ) {
            originalRequest._retry = true;
            try {
                const refreshToken = localStorage.getItem("refreshToken");
                const res = await axios.post("http://localhost:8080/credentials/refresh", {
                    access: localStorage.getItem("accessToken"),
                    refresh: refreshToken,
                });

                const { access, refresh } = res.data;
                localStorage.setItem("accessToken", access);
                localStorage.setItem("refreshToken", refresh);

                // Повторяем исходный запрос с новым access токеном
                originalRequest.headers["Authorization"] = `Bearer ${access}`;
                return api(originalRequest);
            } catch (err) {
                console.error("Refresh token failed", err);
                localStorage.clear();
                window.location.href = "/login";
                return Promise.reject(err);
            }
        }

        return Promise.reject(error);
    }
);

export default api;
