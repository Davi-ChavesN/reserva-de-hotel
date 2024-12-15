package pro.orbolato.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.orbolato.hotel.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
