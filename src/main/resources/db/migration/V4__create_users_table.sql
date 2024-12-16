CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'USER')),
                       enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- Usuário administrador padrão
INSERT INTO users (username, password, role, enabled)
VALUES ('admin', '$2a$12$PsZIQw5ZI9vqNY/SKXGB4ec4sl1BEmm9VxCOIzHcysrLclC2jmekC', 'ADMIN', TRUE);
