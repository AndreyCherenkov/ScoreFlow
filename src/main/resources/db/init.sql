-- =====================================================
-- SCHEMA INIT
-- =====================================================
SET search_path TO public;

-- =====================================================
-- 1. ENUM TYPES
-- =====================================================
CREATE TYPE application_status AS ENUM (
    'NEW',
    'IN_REVIEW',
    'APPROVED',
    'REJECTED'
);

CREATE TYPE document_type AS ENUM (
    'APPLICATION_REPORT',
    'SCORING_REPORT',
    'CREDIT_REPORT',
    'CONTRACT',
    'OTHER'
);

-- =====================================================
-- 2. REFERENCE TABLES
-- =====================================================
CREATE TABLE employment_types
(
    employment_id SERIAL PRIMARY KEY,
    name          TEXT NOT NULL UNIQUE
);

INSERT INTO employment_types (name)
VALUES ('FULL_TIME'),
       ('PART_TIME'),
       ('SELF_EMPLOYED');

-- =====================================================
-- 3. CORE TABLES
-- =====================================================
CREATE TABLE loan_applications
(
    application_id     UUID PRIMARY KEY,
    amount             NUMERIC(10, 2)           NOT NULL,
    application_status application_status       NOT NULL,
    term_month         INT,
    purpose            TEXT,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE customers
(
    customer_id UUID PRIMARY KEY,

    first_name  VARCHAR(25) NOT NULL,
    second_name VARCHAR(25) NOT NULL,
    patronymic  VARCHAR(25),

    birth_date  DATE NOT NULL
        CHECK (age(birth_date) >= INTERVAL '18 years'),

    password VARCHAR(66) NOT NULL,

    passport_series VARCHAR(4) NOT NULL,
    passport_number VARCHAR(6) NOT NULL,
    UNIQUE (passport_series, passport_number),

    income DECIMAL(10, 2) NOT NULL,

    employment_id INT NOT NULL
        REFERENCES employment_types (employment_id),

    loan_application UUID
        REFERENCES loan_applications (application_id),

    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20) NOT NULL UNIQUE
);

-- =====================================================
-- 4. AUTH / SECURITY
-- =====================================================
CREATE TABLE refresh_tokens
(
    token_id        BIGSERIAL PRIMARY KEY,
    token_hash      VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    NOT NULL,
    expiration_date TIMESTAMP    NOT NULL,
    revoked         BOOLEAN      NOT NULL DEFAULT FALSE,

    customer_id     UUID NOT NULL,

    CONSTRAINT fk_refresh_tokens_customer
        FOREIGN KEY (customer_id)
            REFERENCES customers (customer_id)
            ON DELETE CASCADE
);

-- =====================================================
-- 5. SCORING & DECISIONS
-- =====================================================
CREATE TABLE scoring_results
(
    scoring_id     UUID PRIMARY KEY,
    score          INT                      NOT NULL,
    calculated_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    version        INT                      NOT NULL,

    application_id UUID NOT NULL
        REFERENCES loan_applications (application_id),

    UNIQUE (application_id, version)
);

CREATE TABLE decisions
(
    decision_id    UUID PRIMARY KEY,
    decision_date  DATE        NOT NULL,

    application_id UUID UNIQUE NOT NULL
        REFERENCES loan_applications (application_id)
);

CREATE TABLE credit_reports
(
    credit_report_id     UUID PRIMARY KEY,
    delinquencies_number INT         NOT NULL,
    bureau_score         INT         NOT NULL,

    application_id       UUID UNIQUE NOT NULL
        REFERENCES loan_applications (application_id)
);

-- =====================================================
-- 6. DECISION REASONS
-- =====================================================
CREATE TABLE decision_reasons
(
    reason_id   INT PRIMARY KEY,
    code        VARCHAR(10) UNIQUE NOT NULL,
    description TEXT               NOT NULL
);

CREATE TABLE decision_reason_map
(
    decision_id UUID NOT NULL
        REFERENCES decisions (decision_id),

    reason_id   INT  NOT NULL
        REFERENCES decision_reasons (reason_id),

    PRIMARY KEY (decision_id, reason_id)
);

-- =====================================================
-- 7. DOCUMENTS (MAIN TABLE)
-- =====================================================
CREATE TABLE documents
(
    document_id   UUID PRIMARY KEY,

    file_name     VARCHAR(255) NOT NULL,
    content_type  VARCHAR(100) NOT NULL,
    file_size     BIGINT       NOT NULL,

    document_type document_type NOT NULL,

    storage_type  VARCHAR(20)   NOT NULL, -- DB / FILESYSTEM / S3
    storage_path  TEXT          NOT NULL,

    created_at    TIMESTAMP WITH TIME ZONE NOT NULL,

    application_id UUID
        REFERENCES loan_applications(application_id),

    customer_id UUID
        REFERENCES customers(customer_id),

    decision_id UUID
        REFERENCES decisions(decision_id)
);

-- =====================================================
-- 8. DOCUMENT VERSIONING
-- =====================================================
CREATE TABLE document_versions
(
    version_id   UUID PRIMARY KEY,

    document_id  UUID NOT NULL
        REFERENCES documents(document_id)
            ON DELETE CASCADE,

    version      INT NOT NULL,

    storage_path TEXT   NOT NULL,
    file_size    BIGINT NOT NULL,

    created_at   TIMESTAMP WITH TIME ZONE NOT NULL,

    UNIQUE (document_id, version)
);

-- =====================================================
-- 9. OPTIONAL: STORE FILES IN DB
-- =====================================================
CREATE TABLE document_contents
(
    document_id UUID PRIMARY KEY
        REFERENCES documents(document_id)
            ON DELETE CASCADE,

    content BYTEA NOT NULL
);

-- =====================================================
-- 10. INDEXES (IMPORTANT FOR PERFORMANCE)
-- =====================================================

CREATE INDEX idx_documents_application
    ON documents(application_id);

CREATE INDEX idx_documents_customer
    ON documents(customer_id);

CREATE INDEX idx_documents_decision
    ON documents(decision_id);

CREATE INDEX idx_scoring_application
    ON scoring_results(application_id);

CREATE INDEX idx_refresh_tokens_customer
    ON refresh_tokens(customer_id);