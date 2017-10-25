package org.zerhusen.security.service;

import com.yuyan.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.zerhusen.security.JwtTokenUtil;
import org.zerhusen.security.JwtUser;
import org.zerhusen.security.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    public UserDTO loadUserByToken(HttpServletRequest request) {
        UserDTO userDTO = new UserDTO();
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        List<String> auths = user.getAuthorities().stream()
                .map(ga -> {
                    if (ga.getAuthority().indexOf("ADMIN") != -1) {
                        return "admin";
                    } else {
                        return "user";
                    }
                })
                .collect(Collectors.toList());
        userDTO.setId(user.getId());
        userDTO.setName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setEnabled(user.isEnabled() ? "1" : "0");
        userDTO.setRole(auths);
        return userDTO;
    }
}
