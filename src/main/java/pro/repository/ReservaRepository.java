package pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.orbolato.hotel.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}
