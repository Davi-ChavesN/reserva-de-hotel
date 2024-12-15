package pro.orbolato.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.orbolato.hotel.model.Quarto;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    
}
