package com.domination.proyecto.config;

import com.domination.proyecto.services.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/", "/login", "/registrarse","/css/**", "/js/**", "/img/**", "/public/**", "/pages/**", "/WEB-INF/jsp/**").permitAll()
                    .requestMatchers("/inicio").hasAnyRole("administrador", "cliente","prestador") // Permitir a administradores, clientes y prestadores
                    .requestMatchers("/salas/salasDisponibles/**","/reservas/**").hasAnyRole("cliente", "prestador","administrador") // Permitir a clientes y prestadores
                    .requestMatchers("/inicio","/sedes/**","/salas/**","/reservas/**","/usuario/edit/**").hasAnyRole("prestador","administrador")
                    .requestMatchers("/inicio","/reservas/**","/usuario/edit/**").hasRole("cliente")
                    //.requestMatchers("/inicio","/sedes/**","/salas/**","/reservas/**","/usuario/edit/**").hasRole("prestador")
                    .requestMatchers("/inicio","/sedes/**","/salas/**","/reservas/**","/usuario/**").hasRole("administrador")

                    .anyRequest().authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")
                    .loginProcessingUrl("/ingresar")
                    .defaultSuccessUrl("/inicio", true)
                    .failureHandler(customAuthenticationFailureHandler())//failureUrl("/login?error=true")
                    .permitAll()
            )
            .logout(logout ->
                logout
                    .logoutUrl("/logout")
                    .permitAll()
            )
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                request.getSession().setAttribute("hayError", true);
                request.getSession().setAttribute("mensajeError", "Usuario o contraseña incorrectos.");
                super.setDefaultFailureUrl("/login?error=true");
                super.onAuthenticationFailure(request, response, exception);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}