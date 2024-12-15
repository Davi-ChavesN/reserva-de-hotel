package pro.orbolato.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.ObjectInputFilter.Status;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Reserva {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valorReserva;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    @Enumerated(EnumType.STRING)
    private Status status; 

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Quarto quarto;
}
