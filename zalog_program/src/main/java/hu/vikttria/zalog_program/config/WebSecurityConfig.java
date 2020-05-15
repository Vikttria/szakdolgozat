package hu.vikttria.zalog_program.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("zalogigazgato")
                .password(bCryptPasswordEncoder.encode("secret"))
                .roles("IGAZGATO");
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
                    .successForwardUrl("/zalogfiok")
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

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
