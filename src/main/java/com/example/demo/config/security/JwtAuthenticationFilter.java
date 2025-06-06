package com.example.demo.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Filtro di Servlet che estrae e valida il token JWT da ogni richiesta HTTP.
 *
 * <p><strong>English:</strong> Servlet filter that extracts and validates JWT token from each HTTP request.</p>
 * <p><strong>Italiano:</strong> Filtro di Servlet che estrae e valida il token JWT da ogni richiesta HTTP.</p>
 *
 * <p>Procedura principale in {@link #doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}:</p>
 * <ol>
 *     <li>Estrae header Authorization; se non presente o non inizia con “Bearer ”, passa la richiesta al prossimo filtro.</li>
 *     <li>Se presente, rimuove prefisso “Bearer ” per ottenere il token.</li>
 *     <li>Invoca {@link JwtUtil#validateToken(String)}; se valido, estrae username tramite {@link JwtUtil#getUsernameFromJwt(String)} e ruoli tramite {@link JwtUtil#getRolesFromJwt(String)}.</li>
 *     <li>Carica i dettagli utente con {@link CustomUserDetailsService#loadUserByUsername(String)}</li>
 *     <li>Costruisce un {@link UsernamePasswordAuthenticationToken} con l’utente, le autorità e le informazioni di contesto.</li>
 *     <li>Imposta l’autenticazione in {@link SecurityContextHolder}.</li>
 *     <li>Se il token è scaduto ({@link ExpiredJwtException}) o null’altro errore JWT, salva l’eccezione in request attribute “exception”.</li>
 *     <li>Passa la richiesta al prossimo filtro con {@code filterChain.doFilter(request, response)}.</li>
 * </ol>
 *
 * <p><strong>English:</strong> Main procedure in {@link #doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}:</p>
 * <ol>
 *     <li>Extract Authorization header; if absent or not starting with “Bearer ”, delegate to next filter.</li>
 *     <li>If present, strip “Bearer ” prefix to obtain token.</li>
 *     <li>Call {@link JwtUtil#validateToken(String)}; if valid, extract username via {@link JwtUtil#getUsernameFromJwt(String)} and roles via {@link JwtUtil#getRolesFromJwt(String)}.</li>
 *     <li>Load user details with {@link CustomUserDetailsService#loadUserByUsername(String)}.</li>
 *     <li>Construct a {@link UsernamePasswordAuthenticationToken} with the user, authorities, and request details.</li>
 *     <li>Set authentication in {@link SecurityContextHolder}.</li>
 *     <li>If token is expired ({@link ExpiredJwtException}) or any JWT error, store exception in request attribute “exception.”</li>
 *     <li>Delegate to next filter with {@code filterChain.doFilter(request, response)}.</li>
 * </ol>
 *
 * <p>Il metodo {@link #shouldNotFilter(HttpServletRequest)} evita la validazione JWT per rotte che iniziano con “/api/auth”.</p>
 * <p><strong>English:</strong> The method {@link #shouldNotFilter(HttpServletRequest)} skips JWT validation for routes starting with “/api/auth.”</p>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;


    /**
     * Costruttore iniettato con {@link JwtUtil} e {@link CustomUserDetailsService}.
     *
     * <p><strong>English:</strong> Constructor injected with {@link JwtUtil} and {@link CustomUserDetailsService}.</p>
     * <p><strong>Italiano:</strong> Costruttore iniettato con {@link JwtUtil} e {@link CustomUserDetailsService}.</p>
     *
     * @param jwtUtil             utilità per operazioni JWT (generazione, validazione, parsing)
     *                            <p><strong>English:</strong> utility for JWT operations (generation, validation, parsing).</p>
     *                            <p><strong>Italiano:</strong> utilità per operazioni JWT (generazione, validazione, parsing).</p>
     * @param userDetailsService  servizio personalizzato per caricare dettagli utente
     *                            <p><strong>English:</strong> custom service to load user details.</p>
     *                            <p><strong>Italiano:</strong> servizio personalizzato per caricare dettagli utente.</p>
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Estrae e valida il token JWT per ogni richiesta HTTP.
     *
     * <p><strong>English:</strong> Extracts and validates JWT token for each HTTP request.</p>
     *
     * <p>Se il token è valido, impostazione del contesto di sicurezza con le autorità corrispondenti.
     * In caso di token scaduto o malformato, salva l’eccezione in request attribute “exception”.</p>
     * <p><strong>English:</strong> If token is valid, set security context with corresponding authorities.
     * In case of expired or malformed token, store exception in request attribute “exception.”</p>
     *
     * @param request     oggetto {@link HttpServletRequest} contenente header e body della richiesta
     *                    <p><strong>English:</strong> {@link HttpServletRequest} object containing headers and request body.</p>
     *                    <p><strong>Italiano:</strong> oggetto {@link HttpServletRequest} contenente header e body della richiesta.</p>
     * @param response    oggetto {@link HttpServletResponse} per inviare risposta HTTP
     *                    <p><strong>English:</strong> {@link HttpServletResponse} object to send HTTP response.</p>
     *                    <p><strong>Italiano:</strong> oggetto {@link HttpServletResponse} per inviare risposta HTTP.</p>
     * @param filterChain catena di filtri a cui delegare la richiesta successiva
     *                    <p><strong>English:</strong> filter chain to delegate the request to the next filter.</p>
     *                    <p><strong>Italiano:</strong> catena di filtri a cui delegare la richiesta successiva.</p>
     * @throws ServletException in caso di errori nel processo di filtraggio
     *                          <p><strong>English:</strong> in case of filtering process errors.</p>
     *                          <p><strong>Italiano:</strong> in caso di errori nel processo di filtraggio.</p>
     * @throws IOException      in caso di errori di I/O durante la lettura/scrittura dei dati di richiesta/risposta
     *                          <p><strong>English:</strong> in case of I/O errors while reading/writing request/response data.</p>
     *                          <p><strong>Italiano:</strong> in caso di errori di I/O durante la lettura/scrittura dei dati di richiesta/risposta.</p>
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            try{
                if(jwtUtil.validateToken(token)){
                    String username = jwtUtil.getUsernameFromJwt(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    Set<String> roles = jwtUtil.getRolesFromJwt(token);

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (ExpiredJwtException ex){
                request.setAttribute("exception" , ex);
            } catch (Exception ex){
                request.setAttribute("exception", ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Determina se questo filtro deve essere saltato per la richiesta corrente.
     *
     * <p><strong>English:</strong> Determines if this filter should be skipped for the current request.</p>
     *
     * <p>Salta il filtro per le rotte che iniziano con “/api/auth”, in quanto non richiedono autorizzazione JWT.</p>
     * <p><strong>English:</strong> Skips filtering for routes starting with “/api/auth”, as they don’t require JWT authorization.</p>
     *
     * @param request {@link HttpServletRequest} oggetto della richiesta corrente
     *                <p><strong>English:</strong> {@link HttpServletRequest} object of the current request.</p>
     *                <p><strong>Italiano:</strong> {@link HttpServletRequest} oggetto della richiesta corrente.</p>
     * @return {@code true} se il percorso inizia con “/api/auth” (filtro saltato), {@code false} altrimenti
     *         <p><strong>English:</strong> {@code true} if path starts with “/api/auth” (filter skipped), {@code false} otherwise.</p>
     *         <p><strong>Italiano:</strong> {@code true} se il percorso inizia con “/api/auth” (filtro saltato), {@code false} altrimenti.</p>
     * @throws ServletException in caso di errori nel determinare il percorso
     *                          <p><strong>English:</strong> in case of errors determining the path.</p>
     *                          <p><strong>Italiano:</strong> in caso di errori nel determinare il percorso.</p>
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        String path = request.getServletPath();
        return path.startsWith("/api/auth");
    }
}
