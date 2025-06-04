package com.example.demo.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Configurazione di Spring Security per l’applicazione.
 *
 * <p><strong>English:</strong> Spring Security configuration for the application.</p>
 *
 * <p>Definisce:</p>
 * <ul>
 *     <li>Disabilitazione CSRF poiché l’API è stateless.</li>
 *     <li>Politica di sessione stateless (bearer token).</li>
 *     <li>Regole di autorizzazione per percorsi pubblici e protetti.</li>
 *     <li>Filtro JWT preposto all’estrazione e validazione del token.</li>
 *     <li>Bean per {@link PasswordEncoder} e {@link AuthenticationManager}.</li>
 * </ul>
 *
 * @see SecurityFilterChain
 * @see JwtAuthenticationFilter
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configura la catena di filtri di Spring Security.
     *
     * <p><strong>English:</strong> Configures Spring Security filter chain.</p>
     *
     * <p>Impostazioni principali:</p>
     * <ol>
     *     <li>CSRF disabilitato (API stateless).</li>
     *     <li>Sessione senza stato con {@link SessionCreationPolicy#STATELESS}.</li>
     *     <li>Permette l’accesso a:
     *         <ul>
     *             <li>Qualsiasi rotta sotto “/api/auth/**”.</li>
     *             <li>POST su “/api/account/**” (es. registrazione).</li>
     *         </ul>
     *     </li>
     *     <li>Richiede autenticazione per tutte le altre rotte.</li>
     *     <li>Aggiunge {@link JwtAuthenticationFilter} prima di
     *         {@link UsernamePasswordAuthenticationFilter} per gestire JWT.</li>
     *     <li>Disabilita HTTP Basic, form login e logout.</li>
     * </ol>
     *
     * @param http oggetto {@link HttpSecurity} da configurare
     *             <p><strong>English:</strong> {@link HttpSecurity} object to configure.</p>
     * @return {@link SecurityFilterChain} costruita con le regole specificate
     *         <p><strong>English:</strong> {@link SecurityFilterChain} built with specified rules.</p>
     * @throws Exception in caso di errore nella configurazione della catena di filtri
     *                   <p><strong>English:</strong> if an error occurs while configuring the filter chain.</p>
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // al momento disabilitato perchè le API sono stateless
                .sessionManagement(sm-> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/account/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);
        return http.build();

    }

    /**
     * Bean per la codifica delle password con BCrypt.
     *
     * <p><strong>English:</strong> Bean for encoding passwords using BCrypt.</p>
     *
     * @return {@link PasswordEncoder} configurato per utilizzare {@link BCryptPasswordEncoder}
     *         <p><strong>English:</strong> {@link PasswordEncoder} configured to use {@link BCryptPasswordEncoder}.</p>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Espone {@link AuthenticationManager} come bean per l’autenticazione.
     *
     * <p><strong>English:</strong> Exposes {@link AuthenticationManager} as a bean for authentication.</p>
     *
     * @param authConfig oggetto {@link AuthenticationConfiguration} fornito da Spring
     *                   <p><strong>English:</strong> {@link AuthenticationConfiguration} provided by Spring.</p>
     * @return {@link AuthenticationManager}
     * @throws Exception se non riesce a recuperare l’istanza di {@link AuthenticationManager}
     *                   <p><strong>English:</strong> if unable to obtain {@link AuthenticationManager} instance.</p>
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }
}
