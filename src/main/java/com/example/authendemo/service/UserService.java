package com.example.authendemo.service;

import com.example.authendemo.dto.request.RegisterForm;
import com.example.authendemo.dto.response.UserDto;
import com.example.authendemo.entity.User;
import com.example.authendemo.enums.SystemRole;
import com.example.authendemo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier("mapper")
    private  ModelMapper modelMapper;

    public UserDto save(RegisterForm form) {
        Optional<User> optional = userRepository.findByUsername(form.getUsername());
        if (optional.isPresent()) {
            throw new BadRequestException("Username tồn tại");
        }
        User user = User.builder()
                .password(bCryptPasswordEncoder.encode(form.getPassword()))
                .username(form.getUsername())
                .status(1)
                .role(String.valueOf(SystemRole.ROLE_ADMIN))
                .build();
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public User update(User user) {
        if (!userRepository.findById(user.getId()).isPresent()) return null;
        return userRepository.save(user);
    }

    public boolean delete(Long id) {
        if (!userRepository.findById(id).isPresent()) return false;
        userRepository.deleteById(id);
        return true;

    }

    public User getDetail(Long id) {
        return userRepository.getById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(NotFoundException::new);
    }
}
