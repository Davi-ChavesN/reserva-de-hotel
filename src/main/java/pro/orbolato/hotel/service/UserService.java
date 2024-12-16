package pro.orbolato.hotel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.orbolato.hotel.model.User;
import pro.orbolato.hotel.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    public Optional<User> buscarPorId(Long id) {
        return userRepository.findById(id);
    }

    public void atualizar(Long id, User usuarioAtualizado) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        usuario.setUsername(usuarioAtualizado.getUsername());
        usuario.setRole(usuarioAtualizado.getRole());
        if (usuarioAtualizado.getPassword() != null && !usuarioAtualizado.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioAtualizado.getPassword()));
        }
        userRepository.save(usuario);
    }

    public void salvarOuAtualizar(User usuario, String password) {

        if(usuario.getId() != null){
            if(userRepository.existsById(usuario.getId())) {
                atualizar(usuario.getId(), usuario);
                return;
            }
        }

        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Senha é obrigatória para novo usuário");
        }
        usuario.setPassword(passwordEncoder.encode(password));

        userRepository.save(usuario);
    }
}
