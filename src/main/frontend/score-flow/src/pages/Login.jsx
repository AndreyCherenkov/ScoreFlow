import React, { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { useNavigate, Link } from 'react-router-dom';
import '../App.css';

const Login = () => {
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await login(phone, password);
      navigate('/');
    } catch (err) {
      setError('Неверный логин или пароль');
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card" style={{ maxWidth: '420px', padding: '32px' }}>
        <h2>Вход в Скоринг-Систему</h2>
        {error && <div className="auth-error">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group" style={{ marginBottom: '16px' }}>
            <label>Логин (Телефон):</label>
            <input type="tel" value={phone} onChange={(e) => setPhone(e.target.value)} required placeholder="+79991112233" className="form-input" />
          </div>
          <div className="form-group" style={{ marginBottom: '24px' }}>
            <label>Пароль:</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required placeholder="••••••••" className="form-input" />
          </div>
          <button type="submit" className="btn btn-primary" style={{ width: '100%' }}>Войти</button>
        </form>

        <div className="auth-footer">
          Нет аккаунта? <Link to="/register">Зарегистрироваться</Link>
        </div>
      </div>
    </div>
  );
};

export default Login;