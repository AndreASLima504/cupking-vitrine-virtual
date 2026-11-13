
package com.project.pit.cupking.config;

import com.project.pit.cupking.service.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(usuarioDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(auth -> auth

                        // Finalização exige login
                        .requestMatchers(
                                HttpMethod.POST,
                                "/carrinho/finalizar"
                        ).authenticated()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/pedidos"
                        ).authenticated()

                        // Administração exige perfil ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Rotas públicas
                        .requestMatchers(
                                "/",
                                "/login",
                                "/cadastro",
                                "/recuperar-senha",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/uploads/**",
                                "/cupcakes/**",
                                "/carrinho/**"
                        ).permitAll()

                        // Demais rotas públicas
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/carrinho")
                        .failureUrl("/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
