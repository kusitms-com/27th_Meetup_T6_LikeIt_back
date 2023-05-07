package com.kusitms.hotsixServer.domain.user.service;

import com.kusitms.hotsixServer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail)  {
        return userRepository.findByUserEmail(userEmail)
                .map(this::createUser)
                .orElseThrow(()-> new UsernameNotFoundException(userEmail + "-> 데이터베이스에서 찾을 수 없습니다."));
    }


    private UserDetails createUser(com.kusitms.hotsixServer.domain.user.entity.User user){
        GrantedAuthority grantedAuthority =
                new SimpleGrantedAuthority("ROLE_USER");
            return new User(user.getUserEmail(),user.getPassword(), Collections.singleton(grantedAuthority));
    }

}