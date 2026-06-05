import React, { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { useNavigate, Link } from 'react-router-dom';
import '../App.css';

const Register = () => {
  const { register } = useContext(AuthContext);
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const [formData, setFormData] = useState({
    firstName: '',
    secondName: '',
    patronymic: '',
    birthDate: '',
    passportSeries: '',
    passportNumber: '',
    income: '',
    email: '',
    phone: '',
    password: '',
    employmentId: '1'
  });

  const getMaxBirthDate = () => {
    const today = new Date();
    const year = today.getFullYear() - 18;
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    if (formData.passportSeries.length !== 4 || formData.passportNumber.length !== 6) {
      setError('Серия паспорта должна быть 4 цифры, а номер — 6 цифр.');
      setLoading(false);
      return;
    }

    try {
      const registrationRequest = {
        firstName: formData.firstName,
        secondName: formData.secondName,
        patronymic: formData.patronymic || null,
        birthDate: formData.birthDate,
        password: formData.password,
        passportSeries: formData.passportSeries,
        passportNumber: formData.passportNumber,
        income: parseFloat(formData.income),
        email: formData.email,
        phone: formData.phone,
        employmentId: JSON.parse(formData.employmentId)
      };

      await register(registrationRequest);
      alert('Регистрация прошла успешно!');
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.message || 'Ошибка при регистрации. Проверьте введенные данные.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card" style={{ maxWidth: '640px', padding: '40px' }}>
        <h2>Регистрация нового клиента</h2>

        {error && <div className="auth-error">{error}</div>}

        <form onSubmit={handleSubmit}>

          <h4>Личные данные</h4>
          <div className="form-row">
            <div className="form-group">
              <label>Фамилия *</label>
              <input type="text" name="secondName" value={formData.secondName} onChange={handleChange} required className="form-input" />
            </div>
            <div className="form-group">
              <label>Имя *</label>
              <input type="text" name="firstName" value={formData.firstName} onChange={handleChange} required className="form-input" />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label>Отчество</label>
              <input type="text" name="patronymic" value={formData.patronymic} onChange={handleChange} className="form-input" />
            </div>
            <div className="form-group">
              <label>Дата рождения *</label>
              <input type="date" name="birthDate" max={getMaxBirthDate()} value={formData.birthDate} onChange={handleChange} required className="form-input" />
            </div>
          </div>

          <h4>Паспортные данные</h4>
          <div className="form-row">
            <div className="form-group">
              <label>Серия (4 цифры) *</label>
              <input type="text" name="passportSeries" maxLength="4" pattern="\d{4}" placeholder="1234" value={formData.passportSeries} onChange={handleChange} required className="form-input" />
            </div>
            <div className="form-group">
              <label>Номер (6 цифр) *</label>
              <input type="text" name="passportNumber" maxLength="6" pattern="\d{6}" placeholder="567890" value={formData.passportNumber} onChange={handleChange} required className="form-input" />
            </div>
          </div>

          <h4>Финансы и занятость</h4>
          <div className="form-row">
            <div className="form-group">
              <label>Ежемесячный доход (руб) *</label>
              <input type="number" name="income" min="0" step="0.01" value={formData.income} onChange={handleChange} required className="form-input" />
            </div>
            <div className="form-group">
              <label>Тип занятости *</label>
              <select name="employmentId" value={formData.employmentId} onChange={handleChange} className="form-input">
                <option value="1">Полная занятость</option>
                <option value="2">Неполная занятость</option>
                <option value="3">Самозанятый</option>
                <option value="4">Временно не работает/безработный</option>
              </select>
            </div>
          </div>

          <h4>Контакты и безопасность</h4>
          <div className="form-row">
            <div className="form-group">
              <label>Email *</label>
              <input type="email" name="email" value={formData.email} onChange={handleChange} required className="form-input" />
            </div>
            <div className="form-group">
              <label>Телефон *</label>
              <input type="tel" name="phone" placeholder="+79991112233" value={formData.phone} onChange={handleChange} required className="form-input" />
            </div>
          </div>

          <div className="form-group" style={{ marginTop: '12px' }}>
            <label>Пароль *</label>
            <input type="password" name="password" minLength="6" placeholder="Минимум 6 символов" value={formData.password} onChange={handleChange} required className="form-input" />
          </div>

          <button type="submit" disabled={loading} className="btn btn-primary" style={{ width: '100%', marginTop: '30px', padding: '12px' }}>
            {loading ? 'Регистрация...' : 'Зарегистрироваться'}
          </button>
        </form>

        <div className="auth-footer">
          Уже есть аккаунт? <Link to="/login">Войти</Link>
        </div>
      </div>
    </div>
  );
};

export default Register;