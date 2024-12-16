package pro.orbolato.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.orbolato.hotel.enums.Status;
import pro.orbolato.hotel.model.Quarto;

import java.util.List;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    List<Quarto> findByStatus(Status status);
}
