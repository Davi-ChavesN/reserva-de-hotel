package pro.orbolato.hotel.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;
import pro.orbolato.hotel.enums.Status;

@Entity
@Data
public class Quarto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private String tipo;
    private BigDecimal preco; 
    private Status status; 
}