package com.example.authendemo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authendemo.dto.request.RegisterForm;
import com.example.authendemo.dto.response.ApiResponse;
import com.example.authendemo.dto.response.Msg;
import com.example.authendemo.entity.User;
import com.example.authendemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, path = "register")
    public ResponseEntity<ApiResponse> save(@RequestBody RegisterForm form) {
        throw new BadRequestException("hello");
//        Msg msg = Msg.builder().timestamp(LocalDateTime.now().toString())
//                .status(String.valueOf(HttpStatus.OK.value()))
//                .message(HttpStatus.OK.name())
//                .build();
//        return new ResponseEntity<>(
//                ApiResponse.builder()
//                        .msg(msg)
//                        .data(userService.save(form))
//                        .build(), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<User> getAll() {

        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "edit")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "delete")
    public boolean delete(@RequestParam(value = "id") Long id) {
        return userService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "detail/{id}")
    public User getDetail(@PathVariable Long id) {
        return userService.getDetail(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader(AUTHORIZATION);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            try {
                String refresh_token = authorization.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUserByUsername(username);

                String access_token = JWT.create().withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 24 * 360 * 100))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", new ArrayList<>(Collections.singleton(user.getRole())))
                        .sign(algorithm);
                HashMap<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                throw new ForbiddenException();
            }

        } else {
            throw new RuntimeException("Refresh token is missing.");
        }
    }

}
