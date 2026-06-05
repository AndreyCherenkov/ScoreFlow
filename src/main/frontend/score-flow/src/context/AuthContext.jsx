import React, { createContext, useState, useEffect } from 'react';
import API from '../api/axios';

export const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    const storedUserId = localStorage.getItem('userId');

    if (token && storedUserId) {
      setUser({
        authenticated: true,
        id: storedUserId
      });
    } else {
      logout();
    }
    setLoading(false);
  }, []);

  const login = async (phone, password) => {
    const response = await API.post('/auth/login', { phone, password });

    const accessToken = response.data.accessToken;
    const refreshToken = response.data.refreshToken;
    const userId = response.data.userId || response.data.customerId;

    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
    localStorage.setItem('userId', userId);

    setUser({
      authenticated: true,
      id: userId
    });

    return response.data;
  };

  const register = async (userData) => {
    return await API.post('/customer/register', userData);
  };

  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('userId');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout, loading }}>
      {!loading && children}
    </AuthContext.Provider>
  );
};