SET search_path TO public;

-- 1. Справочники и независимые объекты
CREATE TYPE application_status AS ENUM (
    'NEW',
    'IN_REVIEW',
    'APPROVED',
    'REJECTED'
);

CREATE TABLE employment_types
(
    employment_id INT PRIMARY KEY,
    name          TEXT
);

-- 2. Customers (зависит от employment_types)
CREATE TABLE customers
(
    customer_id UUID PRIMARY KEY,
    first_name  VARCHAR(25) NOT NULL,
    second_name VARCHAR(25) NOT NULL,
    patronymic  VARCHAR(25),
    birth_date  DATE NOT NULL CHECK (age(birth_date) >= INTERVAL '18 years'),
    passport_series CHAR(4) NOT NULL,
    passport_number CHAR(6) NOT NULL,
    UNIQUE(passport_series, passport_number),
    income DECIMAL(10, 2) NOT NULL,
    employment_id INT NOT NULL REFERENCES employment_types(employment_id),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20) UNIQUE
);

-- 3. Основная таблица заявок
CREATE TABLE loan_applications
(
    application_id     UUID PRIMARY KEY,
    amount             NUMERIC(10, 2) NOT NULL,
    application_status application_status NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    term_month         INT,
    purpose            TEXT,
    customer_id        UUID NOT NULL REFERENCES customers(customer_id)
);

-- 4. Зависимые от заявок
CREATE TABLE scoring_results
(
    scoring_id     UUID PRIMARY KEY,
    score          INT NOT NULL,
    calculated_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    version        INT NOT NULL,
    application_id UUID NOT NULL REFERENCES loan_applications(application_id),
    UNIQUE (application_id, version)
);

CREATE TABLE decisions
(
    decision_id    UUID PRIMARY KEY,
    decision_date  DATE NOT NULL,
    application_id UUID UNIQUE NOT NULL REFERENCES loan_applications(application_id)
);

CREATE TABLE credit_reports
(
    credit_reports_id UUID PRIMARY KEY,
    delinquencies_number INT NOT NULL,
    bureau_score         INT NOT NULL,
    application_id       UUID UNIQUE NOT NULL REFERENCES loan_applications(application_id)
);

-- 5. Причины решений
CREATE TABLE decision_reasons
(
    reason_id   INT PRIMARY KEY,
    code        VARCHAR(10) UNIQUE NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE decision_reason_map
(
    decision_id UUID NOT NULL REFERENCES decisions(decision_id),
    reason_id   INT NOT NULL REFERENCES decision_reasons(reason_id),
    PRIMARY KEY (decision_id, reason_id)
);