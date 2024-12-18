package pro.orbolato.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pro.orbolato.hotel.enums.TipoUser;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUser role;

    @Column(nullable = false)
    private boolean enabled;
}
