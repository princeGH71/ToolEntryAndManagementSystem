package com.prince.ToolEntrySystem.service;

import com.prince.ToolEntrySystem.dto.SignUpDto;
import com.prince.ToolEntrySystem.dto.UserDto;
import com.prince.ToolEntrySystem.entity.User;
import com.prince.ToolEntrySystem.exceptions.ResourceNotFoundException;
import com.prince.ToolEntrySystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User is implementing UserDetails so we are returning direct user
//        , else we would have build Userdetails object from User
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("User with {" + username +"} not found"));
        
        return user;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id "+ userId +
                " not found"));
    }

    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        
        if(user.isPresent()){
            throw new BadCredentialsException("User already exists "+ signUpDto.getEmail());
        }
        
        User newUser = modelMapper.map(signUpDto, User.class);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        
        User saved = userRepository.save(newUser);
        return modelMapper.map(saved, UserDto.class);        
    }
}
