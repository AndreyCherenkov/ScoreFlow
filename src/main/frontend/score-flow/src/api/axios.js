import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});

API.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem('accessToken');

    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

API.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (
      error.response?.status === 401 &&
      !originalRequest._retry
    ) {
      originalRequest._retry = true;

      try {
        const refreshToken =
          localStorage.getItem('refreshToken');

        if (!refreshToken) {
          throw new Error('Refresh token not found');
        }

        const response = await axios.post(
          'http://localhost:8080/api/v1/auth/refresh',
          {
            refreshToken,
          }
        );

        const {
          accessToken,
          refreshToken: newRefreshToken,
        } = response.data;

        localStorage.setItem(
          'accessToken',
          accessToken
        );

        localStorage.setItem(
          'refreshToken',
          newRefreshToken
        );

        originalRequest.headers.Authorization =
          `Bearer ${accessToken}`;

        return API(originalRequest);
      } catch (refreshError) {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');

        window.location.replace('/login');

        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);

export default API;