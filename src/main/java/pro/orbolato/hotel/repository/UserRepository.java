package pro.orbolato.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.orbolato.hotel.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
