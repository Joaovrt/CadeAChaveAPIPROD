CREATE TABLE IF NOT EXISTS historico (
    id SERIAL PRIMARY KEY,
    professor_id INT NOT NULL REFERENCES professores(id),
    sala_id INT NOT NULL REFERENCES salas(id),
    horario TIMESTAMP NOT NULL,
    abriu boolean NOT NULL
);