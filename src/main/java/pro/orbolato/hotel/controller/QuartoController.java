package pro.orbolato.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.orbolato.hotel.model.Quarto;
import pro.orbolato.hotel.service.QuartoService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService quartoService;

    @GetMapping("/quartos")
    public String listarQuartos(Model model, Pageable pageable) {
        Page<Quarto> quartos = quartoService.buscarTodos(pageable);
        model.addAttribute("quartos", quartos);
        model.addAttribute("title", "Lista de Quartos");
        model.addAttribute("content", "quarto/lista");
        return "index";
    }


    @GetMapping("/novo")
    public String novoQuartoForm(Model model) {
        model.addAttribute("quarto", new Quarto());
        return "quarto/form";
    }

    @PostMapping
    public String salvarQuarto(@ModelAttribute Quarto quarto) {
        quartoService.criar(quarto);
        return "redirect:/quartos";
    }

    @GetMapping("/{id}/editar")
    public String editarQuartoForm(@PathVariable Long id, Model model) {
        Quarto quarto = quartoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));
        model.addAttribute("quarto", quarto);
        return "quarto/form";
    }

    @PostMapping("/{id}")
    public String atualizarQuarto(@PathVariable Long id, @ModelAttribute Quarto quarto) {
        quartoService.atualizar(id, quarto);
        return "redirect:/quartos";
    }

    @PostMapping("/{id}/deletar")
    public String deletarQuarto(@PathVariable Long id) {
        quartoService.deletar(id);
        return "redirect:/quartos";
    }
}
