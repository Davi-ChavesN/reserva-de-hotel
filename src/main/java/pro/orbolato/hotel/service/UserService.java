package pro.orbolato.hotel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.orbolato.hotel.model.User;
import pro.orbolato.hotel.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<User> listarTodos(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> buscarPorId(Long id) {
        return userRepository.findById(id);
    }

    public void salvarOuAtualizar(User usuario, String password) {
        if (usuario.getId() != null) { // Caso de atualização
            User existente = userRepository.findById(usuario.getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + usuario.getId()));

            if (password != null && !password.isBlank()) {
                existente.setPassword(passwordEncoder.encode(password));
            }

            existente.setUsername(usuario.getUsername());
            existente.setRole(usuario.getRole());
            userRepository.save(existente);
        } else {
            if (password == null || password.isBlank()) {
                throw new IllegalArgumentException("Senha é obrigatória para novo usuário.");
            }
            usuario.setPassword(passwordEncoder.encode(password));
            userRepository.save(usuario);
        }
    }

    public void atualizar(Long id, User usuario) {
        salvarOuAtualizar(usuario, null); // Atualização sem alterar a senha
    }
}
