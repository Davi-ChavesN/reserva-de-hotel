package pro.orbolato.hotel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pro.orbolato.hotel.enums.Status;
import pro.orbolato.hotel.model.Quarto;
import pro.orbolato.hotel.repository.QuartoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuartoService {

    private final QuartoRepository quartoRepository;

    public Optional<Quarto> buscarPorId(Long id) {
        return quartoRepository.findById(id);
    }

    public Quarto criar(Quarto quarto) {
        return quartoRepository.save(quarto);
    }

    public void deletar(Long id) {
        if(quartoRepository.existsById(id)){
            quartoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Quarto com ID " + id + " não encontrado");
        }
    }

    public Quarto atualizar(Long id, Quarto quarto) {
        return quartoRepository.findById(id).map(q -> {
            q.setNumero(quarto.getNumero());
            q.setTipo(quarto.getTipo());
            q.setPreco(quarto.getPreco());
            q.setStatus(quarto.getStatus());
            return quartoRepository.save(q);
        }).orElseThrow(() -> new RuntimeException("Quarto com ID " + id + " não encontrado"));
    }

    public Page<Quarto> buscarTodos(Pageable pageable) {
        return quartoRepository.findAll(pageable);
    }


    public List<Quarto> buscarPorStatus(Status status) {
        return quartoRepository.findByStatus(status);
    }
}
