package pro.orbolato.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import pro.orbolato.hotel.enums.Status;
import pro.orbolato.hotel.enums.StatusReserva;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal valorReserva;

    @Column(name = "data_check_in", nullable = false)
    private LocalDate dataCheckIn;

    @Column(name = "data_check_out",nullable = false)
    private LocalDate dataCheckOut;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "quarto_id", nullable = false)
    private Quarto quarto;
}
