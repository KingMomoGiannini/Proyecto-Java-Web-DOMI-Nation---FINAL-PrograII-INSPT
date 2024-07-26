package com.domination.proyecto.services;


import com.domination.proyecto.models.Administrador;
import com.domination.proyecto.models.Usuario;
import com.domination.proyecto.repositories.AdministradorRepository;
import com.domination.proyecto.repositories.UsuarioRepository;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private AdministradorRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username);
        
        if (usuario != null) {
            return new User(usuario.getNombreUsuario(), usuario.getPassword(), getAuthorities(usuario));
        } 

        Administrador administrador = adminRepository.findByNombreUsuario(username);
        if (administrador != null) {
            return new User(administrador.getNombreUsuario(), administrador.getPassword(), getAuthorities(administrador));
        }

        throw new UsernameNotFoundException("No se encuentra el usuario");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
        // Asigna el rol del usuario como 'cliente' o 'prestador' según corresponda
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+usuario.getRol()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Administrador administrador) {
        // Asigna el rol de 'administrador' para los administradores
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+administrador.getRol()));
    }
}