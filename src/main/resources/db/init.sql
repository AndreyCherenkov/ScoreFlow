CREATE TABLE loan_applications(
    application_id UUID PRIMARY KEY,
    amount NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
)