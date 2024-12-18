package pro.orbolato.hotel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pro.orbolato.hotel.enums.Status;
import pro.orbolato.hotel.enums.StatusReserva;
import pro.orbolato.hotel.model.Quarto;
import pro.orbolato.hotel.model.Reserva;
import pro.orbolato.hotel.repository.QuartoRepository;
import pro.orbolato.hotel.repository.ReservaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final QuartoRepository quartoRepository;
    private final ReservaRepository reservaRepository;

    public void salvarReserva(Reserva reserva) {
        Quarto quarto = reserva.getQuarto();
        System.out.println(quarto.getStatus());
        if (quarto.getStatus() == Status.LIVRE) {
            long dias = ChronoUnit.DAYS.between(reserva.getDataCheckIn(), reserva.getDataCheckOut());

            if (dias <= 0) {
                dias = 1L;
            }
            BigDecimal precoDiario = quarto.getPreco();
            BigDecimal totalDias = BigDecimal.valueOf(dias);

            reserva.setValorReserva(precoDiario.multiply(totalDias));
            reserva.setStatus(StatusReserva.AGENDADO);
            quartoRepository.save(quarto);
            reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("Quarto não está disponível para reserva.");
        }
    }

    public void realizarCheckIn(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        Quarto quarto = reserva.getQuarto();

            if (quarto.getStatus() == Status.LIVRE) {
            quarto.setStatus(Status.OCUPADO);
            reserva.setStatus(StatusReserva.UTILIZANDO);
            quartoRepository.save(quarto);
            reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("Quarto não está reservado.");
        }
    }

    public void realizarCheckOut(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        Quarto quarto = reserva.getQuarto();

        if (reserva.getStatus() == StatusReserva.UTILIZANDO) {
            quarto.setStatus(Status.LIVRE);
            reserva.setStatus(StatusReserva.PAGO);
            quartoRepository.save(quarto);
            reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("Reserva não está em uso.");
        }
    }

    public Page<Reserva> listarTodas(Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }

    public void deletarReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reserva com ID " + id + " não encontrada");
        }
    }
}
