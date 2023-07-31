package com.erich.dev.security;

import com.erich.dev.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepo;

    public CustomUserServiceImpl(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return  clientRepo.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Username  no encontrado!"));
    }
}
