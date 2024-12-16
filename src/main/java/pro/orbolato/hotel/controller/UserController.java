package pro.orbolato.hotel.controller;

import lombok.RequiredArgsConstructor;
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
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", userService.listarTodos());
        return "usuario/lista"; // Template da lista de usuários
    }

    @GetMapping("/novo")
    public String novoUsuarioForm(Model model) {
        model.addAttribute("usuario", new User());
        return "usuario/form"; // Template do formulário de novo usuário
    }

    @GetMapping("/editar/{id}")
    public String editarUsuarioForm(@PathVariable Long id, Model model) {
        User usuario = userService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        model.addAttribute("usuario", usuario);
        return "usuario/form"; // Template do formulário de edição
    }

    @PostMapping("/{id}")
    public String atualizarUsuario(@PathVariable Long id, @ModelAttribute User usuario) {
        userService.atualizar(id, usuario);
        return "redirect:/usuarios"; // Redireciona para a lista de usuários
    }

    @PostMapping
    public String salvarOuAtualizarUsuario(@ModelAttribute User usuario, @RequestParam(required = false) String password) {
        userService.salvarOuAtualizar(usuario, password);
        return "redirect:/usuarios";
    }

}
