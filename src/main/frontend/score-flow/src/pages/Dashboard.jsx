import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../api/axios';
import { AuthContext } from '../context/AuthContext';
import '../App.css';

const Dashboard = () => {
  const { logout, user } = useContext(AuthContext);
  const navigate = useNavigate();

  const [applications, setApplications] = useState([]);
  const [statusFilter, setStatusFilter] = useState('');
  const [scores, setScores] = useState({});

  const [formData, setFormData] = useState({
    amount: '',
    termsMonths: '12',
    purpose: 'CAR',
    monthlyIncome: '',
    existingDebt: '0'
  });

  const fetchApplications = async () => {
    try {
      const params = {};
      if (statusFilter) params.status = statusFilter;
      if (user?.id) params.userId = user.id;

      const response = await API.get('/applications', { params });
      setApplications(response.data);
    } catch (err) {
      alert('Ошибка при загрузке списка заявок');
    }
  };

  useEffect(() => {
    fetchApplications();
  }, [statusFilter, user]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleCreateApplication = async (e) => {
    e.preventDefault();
    if (!user?.id) {
      alert('Ошибка: Отсутствует идентификатор клиента. Пожалуйста, перезайдите в систему.');
      return;
    }

    try {
      const payload = {
        customerId: user.id,
        amount: parseFloat(formData.amount),
        termsMonths: parseInt(formData.termsMonths, 10),
        purpose: formData.purpose,
        monthlyIncome: parseFloat(formData.monthlyIncome),
        existingDebt: parseFloat(formData.existingDebt)
      };

      await API.post('/applications', payload);
      setFormData({
        amount: '',
        termsMonths: '12',
        purpose: 'CAR',
        monthlyIncome: '',
        existingDebt: '0'
      });
      alert('Кредитная заявка успешно создана!');
      fetchApplications();
    } catch (err) {
      alert(err.response?.data?.message || 'Не удалось создать заявку. Проверьте данные.');
    }
  };

  const handleComputeScore = async (id) => {
    try {
      const response = await API.post(`/score/${id}`);
      const scoringResult = response.data;

      setScores(prev => ({ ...prev, [id]: scoringResult }));

      setApplications(prevApplications =>
        prevApplications.map((app) => {
          const appId = app.id || app.applicationId;
          if (appId === id) {
            return { ...app, applicationStatus: scoringResult.applicationStatus };
          }
          return app;
        })
      );
      alert(`Скоринг успешно выполнен! Статус: ${scoringResult.applicationStatus}`);
    } catch (err) {
      alert(err.response?.data?.message || 'Ошибка при расчете скоринга');
    }
  };

  const handleDeleteApplication = async (id) => {
    if (!window.confirm('Вы действительно хотите удалить эту заявку?')) return;
    try {
      await API.delete(`/applications/${id}`);
      fetchApplications();
    } catch (err) {
      alert('Не удалось удалить заявку');
    }
  };

  // Вспомогательный метод для динамической подстановки css-классов статуса
  const getStatusClassName = (status) => {
    switch (status) {
      case 'APPROVED': return 'status-badge status-approved';
      case 'REJECTED': return 'status-badge status-rejected';
      case 'IN_REVIEW': return 'status-badge status-review';
      default: return 'status-badge status-new';
    }
  };

  return (
    <div className="dashboard-container">

      {/* Шапка Панели */}
      <header className="dashboard-header">
        <h2>Панель кредитного скоринга</h2>
        <div className="header-user-block">
          <span className="client-id-badge">
            ID Клиента: <strong>{user?.id || 'Неизвестно'}</strong>
          </span>

          <button
            onClick={() => navigate('/profile')}
            className="btn btn-primary btn-sm"
          >
            Личный кабинет
          </button>

          <button
            onClick={logout}
            className="btn btn-danger btn-sm"
          >
            Выйти
          </button>
        </div>
      </header>

      {/* Форма создания новой заявки */}
      <section className="card-section">
        <h3>Создать новую заявку на кредит</h3>
        <form onSubmit={handleCreateApplication}>
          <div className="form-grid">
            <div className="form-group">
              <label>Сумма кредита (₽) *</label>
              <input type="number" name="amount" min="0" step="0.01" placeholder="Например, 500000" value={formData.amount} onChange={handleInputChange} required className="form-input" />
            </div>
            <div className="form-group">
              <label>Срок кредитования (мес.) *</label>
              <input type="number" name="termsMonths" min="1" placeholder="12" value={formData.termsMonths} onChange={handleInputChange} required className="form-input" />
            </div>
            <div className="form-group">
              <label>Цель кредита *</label>
              <select name="purpose" value={formData.purpose} onChange={handleInputChange} className="form-input">
                <option value="CAR">Покупка автомобиля (CAR)</option>
              </select>
            </div>
            <div className="form-group">
              <label>Ваш месячный доход (₽) *</label>
              <input type="number" name="monthlyIncome" min="0" step="0.01" placeholder="Доход в рублях" value={formData.monthlyIncome} onChange={handleInputChange} required className="form-input" />
            </div>
            <div className="form-group">
              <label>Текущие долги (₽) *</label>
              <input type="number" name="existingDebt" min="0" step="0.01" placeholder="Сумма задолженностей" value={formData.existingDebt} onChange={handleInputChange} required className="form-input" />
            </div>
          </div>
          <button type="submit" className="btn btn-success">Отправить заявку</button>
        </form>
      </section>

      {/* Секция таблицы заявок */}
      <section className="card-section">
        <div className="section-header-inline">
          <h3>Список ваших заявок</h3>
          <div className="filter-block">
            <label>Статус:</label>
            <select value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)} className="form-input">
              <option value="">Все статусы</option>
              <option value="NEW">NEW</option>
              <option value="IN_REVIEW">IN_REVIEW</option>
              <option value="APPROVED">APPROVED</option>
              <option value="REJECTED">REJECTED</option>
            </select>
          </div>
        </div>

        <div className="table-responsive">
          <table className="custom-table">
            <thead>
              <tr>
                <th>ID Заявки</th>
                <th>Сумма</th>
                <th>Срок</th>
                <th>Цель</th>
                <th>Статус</th>
                <th>Результат Скоринга</th>
                <th style={{ textAlign: 'center' }}>Действия</th>
              </tr>
            </thead>
            <tbody>
              {applications.map((app) => {
                const appId = app.id || app.applicationId;
                const currentScoreData = scores[appId];

                return (
                  <tr key={appId}>
                    <td className="app-id-cell">{appId}</td>
                    <td style={{ fontWeight: '600' }}>{app.amount?.toLocaleString()} ₽</td>
                    <td>{app.termsMonths ? `${app.termsMonths} мес.` : '—'}</td>
                    <td><span className="purpose-tag">{app.purpose || 'CAR'}</span></td>
                    <td>
                      <span className={getStatusClassName(app.applicationStatus)}>
                        {app.applicationStatus || 'NEW'}
                      </span>
                    </td>
                    <td>
                      {currentScoreData ? <span className="score-text">Баллы: {currentScoreData.score}</span> : '—'}
                    </td>
                    <td>
                      <div className="actions-cell">
                        <button
                          onClick={() => handleComputeScore(appId)}
                          disabled={app.applicationStatus === 'APPROVED' || app.applicationStatus === 'REJECTED'}
                          className="btn btn-primary btn-sm"
                        >
                          Запустить скоринг
                        </button>
                        <button onClick={() => handleDeleteApplication(appId)} className="btn btn-danger btn-sm">
                          Удалить
                        </button>
                      </div>
                    </td>
                  </tr>
                );
              })}
              {applications.length === 0 && (
                <tr>
                  <td colSpan="7" style={{ padding: '30px', textAlign: 'center', color: 'var(--text-secondary)' }}>Заявок не найдено</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </section>
    </div>
  );
};

export default Dashboard;