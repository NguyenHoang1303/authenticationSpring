package com.example.authendemo.config;

import com.example.authendemo.security.UserAuthenticationFilter;
import com.example.authendemo.security.UserAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.authendemo.constant.URL.*;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UserAuthenticationFilter userAuthenticationFilter =
                new UserAuthenticationFilter(authenticationManagerBean());

        userAuthenticationFilter.setFilterProcessesUrl(URL_LOGIN);
//        http.authorizeHttpRequests().antMatchers(USER + "/**").hasAnyAuthority("ROLE_USER");
        http.cors().and().csrf().disable().authorizeHttpRequests()
                .antMatchers(URL_LOGIN, URL_REGISTER).permitAll()
                .anyRequest().authenticated().and().httpBasic();

        http.addFilter(userAuthenticationFilter);
        http.addFilterBefore(new UserAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
