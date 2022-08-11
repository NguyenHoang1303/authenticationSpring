package com.example.authendemo.security;

import com.example.authendemo.entity.User;
import com.example.authendemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existUser = repository.findByUsername(username).orElseThrow(NotFoundException::new);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(existUser.getRole()));
        return new org.springframework.security.core.userdetails.User(
                existUser.getUsername(), existUser.getPassword(),authorities);
    }
}
