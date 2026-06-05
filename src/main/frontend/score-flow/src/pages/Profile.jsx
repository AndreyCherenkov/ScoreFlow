import React, { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axios";
import { AuthContext } from "../context/AuthContext";

const Profile = () => {
    const { user } = useContext(AuthContext);
    const navigate = useNavigate();

    const [profile, setProfile] = useState(null);

    useEffect(() => {
        if (user?.id) {
            loadProfile();
        }
    }, [user]);

    const loadProfile = async () => {
        try {
            const response = await API.get(`/profile/${user.id}`);
            setProfile(response.data);
        } catch (e) {
            alert("Ошибка загрузки профиля");
        }
    };

    if (!profile) {
        return (
            <div className="dashboard-container">
                <div className="card-section">
                    <h3>Загрузка профиля...</h3>
                </div>
            </div>
        );
    }

    return (
        <div className="dashboard-container">

            <header className="dashboard-header">
                <h2>Личный кабинет</h2>

                <div className="header-user-block">
                    <span className="client-id-badge">
                        ID: <strong>{profile.customerId}</strong>
                    </span>

                    <button
                        className="btn btn-primary btn-sm"
                        onClick={() => navigate("/")}
                    >
                        ← Мои заявки
                    </button>
                </div>
            </header>

            <section className="card-section profile-card">

                <div className="profile-top">
                    <div className="profile-avatar">
                        {profile.firstName?.charAt(0)}
                        {profile.secondName?.charAt(0)}
                    </div>

                    <div>
                        <h2 className="profile-name">
                            {profile.secondName} {profile.firstName}
                        </h2>

                        <p className="profile-subtitle">
                            Клиент системы кредитного скоринга
                        </p>
                    </div>
                </div>

                <div className="profile-grid">

                    <div className="profile-item">
                        <span className="profile-label">
                            Имя
                        </span>
                        <span className="profile-value">
                            {profile.firstName}
                        </span>
                    </div>

                    <div className="profile-item">
                        <span className="profile-label">
                            Фамилия
                        </span>
                        <span className="profile-value">
                            {profile.secondName}
                        </span>
                    </div>

                    <div className="profile-item">
                        <span className="profile-label">
                            Отчество
                        </span>
                        <span className="profile-value">
                            {profile.patronymic || "Не указано"}
                        </span>
                    </div>

                    <div className="profile-item">
                        <span className="profile-label">
                            Дата рождения
                        </span>
                        <span className="profile-value">
                            {profile.birthDate}
                        </span>
                    </div>

                    <div className="profile-item">
                        <span className="profile-label">
                            Email
                        </span>
                        <span className="profile-value">
                            {profile.email}
                        </span>
                    </div>

                    <div className="profile-item">
                        <span className="profile-label">
                            Телефон
                        </span>
                        <span className="profile-value">
                            {profile.phone}
                        </span>
                    </div>

                    <div className="profile-item">
                        <span className="profile-label">
                            Паспорт
                        </span>
                        <span className="profile-value">
                            {profile.passportSeries} {profile.passportNumber}
                        </span>
                    </div>

                    <div className="profile-item">
                        <span className="profile-label">
                            Доход
                        </span>
                        <span className="profile-value income">
                            {Number(profile.income).toLocaleString()} ₽
                        </span>
                    </div>

                    <div className="profile-item profile-item-full">
                        <span className="profile-label">
                            Место работы
                        </span>
                        <span className="profile-value">
                            {profile.employmentName || "Не указано"}
                        </span>
                    </div>

                </div>

            </section>
        </div>
    );
};

export default Profile;