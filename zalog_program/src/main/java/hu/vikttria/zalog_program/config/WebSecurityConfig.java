package hu.vikttria.zalog_program.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("zalogigazgato")
                .password(bCryptPasswordEncoder.encode("secret"))
                .roles("IGAZGATO");
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/bevon", "/dolgozo", "/zalogfiok").hasRole("IGAZGATO")
                    .antMatchers( "/felvet", "/hosszabbit", "/kivalt", "/lekerdez", "/ugyfelFelvet").hasRole("DOLGOZO")
                    .antMatchers("/ugyfel").hasRole("UGYFEL")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/bejelentkezes")
                    //.successForwardUrl("/zalogfiok")
                    .successHandler(myAuthenticationSuccessHandler())
                    //.successForwardUrl("/felvet")
                    //.successForwardUrl("/ugyfel")
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/bejelentkezes")
                    .logoutUrl("/kijelentkezes")
                    .clearAuthentication(true)
                    .deleteCookies("my-remember-me-cookie")
                    .permitAll();
    }

    private AuthenticationSuccessHandler myAuthenticationSuccessHandler() {

        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                for (final GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                    String authorityName = grantedAuthority.getAuthority();
                    switch (authorityName) {
                        case "ROLE_DOLGOZO":
                            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/felvet");
                            break;
                        case "ROLE_IGAZGATO":
                            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/zalogfiok");
                            break;
                        case "ROLE_UGYFEL":
                            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/ugyfel");
                            break;
                    }
                }
            }

        };
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
