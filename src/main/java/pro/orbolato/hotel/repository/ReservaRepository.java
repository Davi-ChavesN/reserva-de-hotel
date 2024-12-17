package pro.orbolato.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.orbolato.hotel.model.Quarto;
import pro.orbolato.hotel.model.Reserva;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT q FROM Quarto q WHERE q.id NOT IN " +
            "(SELECT r.quarto.id FROM Reserva r " +
            "WHERE :dataCheckIn <= r.dataCheckOut AND :dataCheckOut >= r.dataCheckIn)")
    List<Quarto> findQuartosDisponiveis(@Param("dataCheckIn") LocalDate dataCheckIn,
                                        @Param("dataCheckOut") LocalDate dataCheckOut);
}
