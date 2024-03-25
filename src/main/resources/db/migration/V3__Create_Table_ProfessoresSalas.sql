CREATE TABLE IF NOT EXISTS professores_salas (
    professor_id INTEGER REFERENCES professores(id) NOT NULL,
    sala_id INTEGER REFERENCES salas(id) NOT NULL,
    PRIMARY KEY (professor_id, sala_id)
);