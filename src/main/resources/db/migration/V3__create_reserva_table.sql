CREATE TABLE reserva (
    id SERIAL PRIMARY KEY,
    valor_reserva DECIMAL(10,2) NOT NULL,
    data_check_in DATE NOT NULL,
    data_check_out DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'Ativa',
    cliente_id INT NOT NULL,
    quarto_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente (id),
    FOREIGN KEY (quarto_id) REFERENCES quarto (id)
);
