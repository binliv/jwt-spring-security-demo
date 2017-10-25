package org.zerhusen.security.controller;

import com.yuyan.web.dto.ResultDTO;
import com.yuyan.web.dto.UserDTO;
import com.yuyan.web.dto.UserListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.model.security.Authority;
import org.zerhusen.model.security.AuthorityName;
import org.zerhusen.model.security.User;
import org.zerhusen.security.JwtTokenUtil;
import org.zerhusen.security.JwtUser;
import org.zerhusen.security.repository.UserRepository;
import org.zerhusen.security.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(path = "/users")
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public UserDTO getAuthenticatedUser(HttpServletRequest request) {
        return userService.loadUserByToken(request);
    }



    @RequestMapping(value = "", method = RequestMethod.GET)
    public UserListDTO getUsers(String name, Pageable pageable) {
        UserListDTO userListDTO = new UserListDTO();
        Page<User> users = null;
        if(StringUtils.isEmpty(name)){
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findByUsernameContaining(name, pageable);
        }
        userListDTO.setTotal(users.getTotalElements());
        List<UserDTO> userItems = new ArrayList<>((int)users.getTotalElements());
        userListDTO.setItems(userItems);
        for(User user: users){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getUsername());
            userDTO.setEnabled(user.getEnabled()?"1":"0");
            List<String> roles = user.getAuthorities().stream().map(authority ->{
            if ( authority.getName().toString().indexOf("ADMIN") != -1) {
                return "admin";
            } else{
                return "user";
            }
            }).collect(Collectors.toList());
            userDTO.setRole(roles);
            userItems.add(userDTO);
        }
        return userListDTO;
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResultDTO createUser(@RequestBody UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(true);
        user.setFirstname("");
        user.setLastname("");
        user.setLastPasswordResetDate(new Date());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        List<Authority> authorities = userDTO.getRole().stream().map(role -> {
            Authority authority = new Authority();
            switch (role){
                case "admin":
                    authority.setId(2L);
                    //authority.setName(AuthorityName.ROLE_ADMIN);
                    return authority;
                default:
                    authority.setId(1L);
                    //authority.setName(AuthorityName.ROLE_USER);
                    return authority;
            }
        }).collect(Collectors.toList());

        user.setAuthorities(authorities);
        userRepository.save(user);
        return new ResultDTO("ok", 0);
    }

    @RequestMapping(value="", method = RequestMethod.PATCH)
    public ResultDTO updateUser(@RequestBody UserDTO userDTO){
        if(userDTO == null || userDTO.getId() == null || userDTO.getId() <= 0){
            return new ResultDTO("无效的用户Id", 1);
        }
        User user = userRepository.findOne(userDTO.getId());
        //只修改name，email， enabled， password
        if(!StringUtils.isEmpty(userDTO.getEmail())){
            user.setEmail(userDTO.getEmail());
        }
        if(!StringUtils.isEmpty(userDTO.getName())){
            user.setUsername(userDTO.getName());
        }
        if(!StringUtils.isEmpty(userDTO.getPassword())){
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if(!StringUtils.isEmpty(userDTO.getEnabled())){
            user.setEnabled(userDTO.getEnabled().equals("1"));
        }
        userRepository.save(user);
        return new ResultDTO("ok", 0);
    }

    @DeleteMapping("/{userID}")
    public ResultDTO deleteUser(@PathVariable("userID") long userID) {
        if (userID <= 0) {
            return new ResultDTO("无效的用户Id", 1);
        }
        userRepository.delete(userID);
        return new ResultDTO("ok", 0);
    }
}
