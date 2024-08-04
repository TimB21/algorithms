CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS lens (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    transmissions VARCHAR(2000) NOT NULL,
    absorption_spectrum VARCHAR(2000) NOT NULL,
    user_id INT REFERENCES "users"(id) ON DELETE CASCADE
);
