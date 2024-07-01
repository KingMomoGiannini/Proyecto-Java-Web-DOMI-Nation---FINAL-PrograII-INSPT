package com.domination.proyecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class RegistroController {
//
//    @Autowired
//    private UsuarioClienteService usuarioClienteService;
//
//    @Autowired
//    private UsuarioPrestadorService usuarioPrestadorService;

    @GetMapping("/registrarse")
    public String showRegistrationForm() {
        return "registro"; // Esto devuelve la vista "registro.jsp"
    }

//    @PostMapping("/registrarse")
//    public String registerUser(@RequestParam String nomCliente, @RequestParam String apeCliente, @RequestParam String celular, 
//                               @RequestParam String email, @RequestParam String user, @RequestParam String pass, 
//                               @RequestParam(required = false) String tipoUsuario, Model model) {
//        try {
//            if ("prestador".equals(tipoUsuario)) {
//                // Si el checkbox se encuentra tildado, crea un usuario de tipo prestador
//                UsuarioPrestador elPrestador = new UsuarioPrestador(user, nomCliente, apeCliente, email, pass, celular);
//                usuarioPrestadorService.registerUser(elPrestador);
//                model.addAttribute("elUsuario", elPrestador);
//            } else {
//                // Por defecto, se crea un usuario de tipo cliente
//                UsuarioCliente elUsuario = new UsuarioCliente(user, nomCliente, apeCliente, email, pass, celular);
//                usuarioClienteService.registerUser(elUsuario);
//                model.addAttribute("elUsuario", elUsuario);
//            }
//            return "felicitacion"; // Esto devuelve la vista "felicitacion.jsp"
//        } catch (Exception ex) {
//            model.addAttribute("error", "Hubo un problema con el registro: " + ex.getMessage());
//            return "error"; // Vista de error (puedes crear una vista error.jsp para manejar esto)
//        }
//    }
    
    

    /*    @Autowired
        private UsuarioClienteRepository usuarioClienteRepository;

        @PostMapping
        @RequestMapping("/registrarse")
        public ModelAndView registrarUsuario(
            @RequestParam("nomCliente") String nombre,
            @RequestParam("apeCliente") String apellido,
            @RequestParam("celular") String celular,
            @RequestParam("email") String email,
            @RequestParam("user") String nombreUsuario,
            @RequestParam("pass") String password,
            @RequestParam(value = "tipoUsuario", required = false) String tipoUsuario
        ) {
            UsuarioCliente usuarioCliente = new UsuarioCliente();
            usuarioCliente.setNomUsuario(nombreUsuario);
            usuarioCliente.setNombre(nombre);
            usuarioCliente.setApellido(apellido);
            usuarioCliente.setEmail(email);
            usuarioCliente.setPassword(password);
            usuarioCliente.setCelular(celular);
            usuarioCliente.setRol("cliente");

            if ("prestador".equals(tipoUsuario)) {
                // Configura cualquier propiedad específica para usuarios de tipo "prestador"
            }

            usuarioClienteRepository.save(usuarioCliente);//.save(usuarioCliente);

            ModelAndView modelAndView = new ModelAndView("registroExitoso");
            modelAndView.addObject("usuario", usuarioCliente);

            return modelAndView;
    }*/
}
