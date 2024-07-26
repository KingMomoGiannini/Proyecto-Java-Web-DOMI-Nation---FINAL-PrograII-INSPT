package com.domination.proyecto.config;

import com.domination.proyecto.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                    .requestMatchers("/inicio","/salas/**","/reservas/**","/usuario/edit/**").hasRole("cliente")
                    .requestMatchers("/inicio","/sedes/**","/salas/**","/reservas/**","/usuario/edit/**").hasRole("prestador")
                    .requestMatchers("/inicio","/sedes/**","/salas/**","/reservas/**","/usuario/**").hasRole("administrador")

                    .anyRequest().authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")
                    .loginProcessingUrl("/ingresar")
                    .defaultSuccessUrl("/inicio", true)
                    .failureUrl("/login?error=true")
                    .permitAll()
            )
            .logout(logout ->
                logout
                    .logoutUrl("/logout")
                    .permitAll()
            )
            .csrf().disable(); 
        return http.build();
    }
//    
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//            .username("username")
//            .password(passwordEncoder().encode("password"))
//            .roles("USER")
//            .build();
//        return new InMemoryUserDetailsManager(user);
//    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}