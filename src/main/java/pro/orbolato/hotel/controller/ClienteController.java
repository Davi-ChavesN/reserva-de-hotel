package pro.orbolato.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.orbolato.hotel.model.Cliente;
import pro.orbolato.hotel.service.ClienteService;

import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = Pageable.ofSize(5).withPage(page);
        Page<Cliente> clientes = clienteService.listarTodos(pageable);
        model.addAttribute("clientes", clientes);
        return "cliente/lista";
    }

    @GetMapping("/novo")
    public String novoClienteForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/form";
    }

    @PostMapping
    public String salvarCliente(@ModelAttribute Cliente cliente, BindingResult result) {
        cliente.setCpf(cliente.getCpf().replace(".", "").replace("-", ""));

        if (result.hasErrors()) {
            return "cliente/form";
        }

        try {
            clienteService.salvar(cliente);
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("cpf", "error.cliente", "CPF já cadastrado. Por favor, insira um CPF diferente.");
            return "cliente/form";
        } catch (Exception e) {
            result.reject("globalError", "Erro ao salvar cliente.");
            return "cliente/form";
        }


        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editarClienteForm(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        model.addAttribute("cliente", cliente);
        return "cliente/form";
    }

    @PostMapping("/{id}")
    public String atualizarCliente(@PathVariable Long id, @ModelAttribute Cliente cliente) {
        cliente.setId(id);
        clienteService.salvar(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/{id}/deletar")
    public String deletarCliente(@PathVariable Long id) {
        clienteService.deletar(id);
        return "redirect:/clientes";
    }
}
