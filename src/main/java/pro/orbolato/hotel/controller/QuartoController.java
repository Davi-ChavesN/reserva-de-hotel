package pro.orbolato.hotel.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import pro.orbolato.hotel.model.Quarto;
import pro.orbolato.hotel.repository.QuartoRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/quarto")
@RequiredArgsConstructor
public class QuartoController {

    private final QuartoRepository quartoRepository;

    @GetMapping("/{id}")
    public Optional<Quarto> getMethodName(@RequestParam Long id) {
        return quartoRepository.findById(id);
    }

    @PostMapping
    public Quarto criarQuarto(@RequestBody Quarto quarto) {
        return quartoRepository.save(quarto);
    }

    @DeleteMapping("/{id}")
    public void deletarQuarto(@RequestParam Long id) {
        quartoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Quarto atualizarQuarto(@RequestParam Long id, @RequestBody Quarto quarto) {
        return quartoRepository.findById(id).map(quartoExistente -> {
            quartoExistente.setPreco(quarto.getPreco());
            quartoExistente.setTipo(quarto.getTipo());
            quartoExistente.setNumero(quarto.getNumero());
            quartoExistente.setStatus(quarto.getStatus());

            return quartoRepository.save(quartoExistente);
        }).orElseThrow(() -> new RuntimeException("Modelo com ID " + id + " não encontrado"));
    }

}
