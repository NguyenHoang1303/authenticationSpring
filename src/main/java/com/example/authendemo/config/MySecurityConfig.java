package com.example.authendemo.config;

import com.example.authendemo.security.MyUserDetailService;
import com.example.authendemo.security.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;





    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UserAuthenticationFilter userAuthenticationFilter =
                new UserAuthenticationFilter(authenticationManagerBean());

        userAuthenticationFilter.setFilterProcessesUrl("/api/v1/users/login");

        http.authorizeHttpRequests()
                .antMatchers("/api/v1/users/add").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
        http.addFilter(userAuthenticationFilter);
//        http.addFilterBefore(new UserAuthenticationFilter());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
