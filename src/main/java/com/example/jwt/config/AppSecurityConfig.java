package com.example.jwt.config;

import com.example.jwt.auth.ApplicationUserService;
import com.example.jwt.jwt.JwtProvider;
import com.example.jwt.jwt.JwtTokenVerifierFilter;
import com.example.jwt.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.jwt.model.ApplicationUserPermission.TASK_WRITE;
import static com.example.jwt.model.ApplicationUserRole.EMPLOYEE;
import static com.example.jwt.model.ApplicationUserRole.TRAINEE;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtProvider))
                .addFilterAfter(new JwtTokenVerifierFilter(jwtProvider), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index").permitAll()
                .antMatchers("manager/api/**").hasRole("MANAGER")
                .antMatchers(HttpMethod.DELETE, "/api/task/**").hasAuthority(TASK_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/task/**").hasAuthority(TASK_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/task/**").hasAuthority(TASK_WRITE.getPermission())
                .antMatchers("/api/task/**").hasAnyRole(EMPLOYEE.name(), TRAINEE.name())
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
