package pro.orbolato.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pro.orbolato.hotel.model.User;
import pro.orbolato.hotel.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String listarUsuarios(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = Pageable.ofSize(5).withPage(page);
        model.addAttribute("usuarios", userService.listarTodos(pageable));
        return "usuario/lista";
    }

    @GetMapping("/novo")
    public String novoUsuarioForm(Model model) {
        model.addAttribute("usuario", new User());
        return "usuario/form";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuarioForm(@PathVariable Long id, Model model) {
        User usuario = userService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        model.addAttribute("usuario", usuario);
        return "usuario/form";
    }

    @PostMapping("/{id}")
    public String atualizarUsuario(@PathVariable Long id, @ModelAttribute User usuario) {
        userService.atualizar(id, usuario);
        return "redirect:/usuarios";
    }

    @PostMapping
    public String salvarOuAtualizarUsuario(@ModelAttribute User usuario, @RequestParam(required = false) String password) {
        userService.salvarOuAtualizar(usuario, password);
        return "redirect:/usuarios";
    }

}
