package fi.haagahelia.bookstore;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        
    }

    private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
            new AntPathRequestMatcher("/h2-console/**"),
            new AntPathRequestMatcher("/css/**")
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST_URLS).permitAll()
                        .requestMatchers("/delete/**").hasRole("ADMIN") // Restrict delete to ADMIN
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // For H2 Console
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/booklist", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .csrf(csrf -> csrf.disable()); // Not for production, only for development

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

        
    }

    // Remove or comment out this method
    /*
     * @Autowired
     * public void configureGlobal(AuthenticationManagerBuilder auth) throws
     * Exception {
     * auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()
     * );
     * }
     */
}