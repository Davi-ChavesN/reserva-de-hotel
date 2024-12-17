package pro.orbolato.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pro.orbolato.hotel.enums.Status;
import pro.orbolato.hotel.enums.StatusReserva;
import pro.orbolato.hotel.model.Reserva;
import pro.orbolato.hotel.model.Quarto;
import pro.orbolato.hotel.model.Cliente;
import pro.orbolato.hotel.repository.QuartoRepository;
import pro.orbolato.hotel.service.ReservaService;
import pro.orbolato.hotel.service.QuartoService;
import pro.orbolato.hotel.service.ClienteService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final QuartoService quartoService;
    private final ClienteService clienteService;
    private final QuartoRepository quartoRepository;

    @GetMapping
    public String listarReservas(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = Pageable.ofSize(5).withPage(page);
        Page<Reserva> reservas = reservaService.listarTodas(pageable);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("reservas", reservas);
        return "reserva/lista";
    }

    @GetMapping("/nova")
    public String novaReservaForm(Model model) {
        List<Quarto> quartosLivres = quartoService.buscarPorStatus(Status.LIVRE);
        List<Cliente> clientes = clienteService.listTodos();

        model.addAttribute("reserva", new Reserva());
        model.addAttribute("quartos", quartosLivres);
        model.addAttribute("clientes", clientes);
        return "reserva/form";
    }

    @PostMapping
    public String salvarReserva(@ModelAttribute Reserva reserva, Model model) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate checkin = reserva.getDataCheckIn();
        LocalDate checkout = reserva.getDataCheckOut();
        List<Quarto> quartosLivres = quartoService.buscarPorStatus(Status.LIVRE);
        List<Cliente> clientes = clienteService.listTodos();

        if (checkin.isAfter(checkout) || checkin.isEqual(checkout)) {
            model.addAttribute("erro", "A data de check-in deve ser antes da data de check-out.");
            model.addAttribute("reserva", new Reserva());
            model.addAttribute("quartos", quartosLivres);
            model.addAttribute("clientes", clientes);
            return "reserva/form";
        }

        if (checkin.isBefore(dataAtual) || checkout.isBefore(dataAtual)) {
            model.addAttribute("erro", "As datas de check-in e check-out não podem ser anteriores à data atual.");
            model.addAttribute("reserva", new Reserva());
            model.addAttribute("quartos", quartosLivres);
            model.addAttribute("clientes", clientes);
            return "reserva/form";
        }

        reserva.setQuarto(quartoRepository.getById(reserva.getQuarto().getId()));
        reservaService.salvarReserva(reserva);
        return "redirect:/reservas";
    }

    @PostMapping("/checkin/{id}")
    public String realizarCheckIn(@PathVariable Long id) {
        reservaService.realizarCheckIn(id);
        return "redirect:/reservas";
    }

    @PostMapping("/checkout/{id}")
    public String realizarCheckOut(@PathVariable Long id) {
        reservaService.realizarCheckOut(id);
        return "redirect:/reservas";
    }

    @PostMapping("/deletar/{id}")
    public String deletarReserva(@PathVariable Long id) {
        reservaService.deletarReserva(id);
        return "redirect:/reservas";
    }
}
