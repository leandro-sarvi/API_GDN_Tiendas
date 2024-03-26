package GDNTiendas.GDNTIENDAS.config.security;

import GDNTiendas.GDNTIENDAS.service.IJwtUtilityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private  final IJwtUtilityService jwtUtilityeService;
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, IJwtUtilityService jwtUtilityeService){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtUtilityeService = jwtUtilityeService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf ->
                        csrf.disable())
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
                    auth.requestMatchers(HttpMethod.POST,"/auth/register").permitAll();
                    auth.anyRequest().authenticated();
                        }

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtilityeService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exeptionHandling ->
                        exeptionHandling.authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized")))
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
