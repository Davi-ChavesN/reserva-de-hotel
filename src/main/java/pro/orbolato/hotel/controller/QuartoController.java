package pro.orbolato.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.orbolato.hotel.enums.Status;
import pro.orbolato.hotel.model.Quarto;
import pro.orbolato.hotel.service.QuartoService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService quartoService;

    @GetMapping
    public String listarQuartos(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = Pageable.ofSize(5).withPage(page);
        model.addAttribute("quartos", quartoService.buscarTodos(pageable));
        return "quarto/lista";
    }

    @GetMapping("/novo")
    public String novoQuartoForm(Model model) {
        Quarto quarto = new Quarto();
        quarto.setStatus(Status.LIVRE);
        model.addAttribute("quarto", quarto);
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

    @GetMapping("/disponiveis")
    @ResponseBody
    public List<Quarto> getQuartosDisponiveis(
            @RequestParam("dataCheckIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCheckIn,
            @RequestParam("dataCheckOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCheckOut) {

        return quartoService.findDisponiveis(dataCheckIn, dataCheckOut);
    }

}
