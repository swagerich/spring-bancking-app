package com.erich.dev.service.impl;

import com.erich.dev.dto.AccountDto;
import com.erich.dev.dto.UsuarioDto;
import com.erich.dev.dto.proyection.JwtResponse;
import com.erich.dev.dto.proyection.LoginRequest;
import com.erich.dev.dto.proyection.SignupRequest;
import com.erich.dev.entity.Account;
import com.erich.dev.entity.Role;
import com.erich.dev.entity.Usuario;
import com.erich.dev.exception.EntityNotFoundException;
import com.erich.dev.exception.OperationNotAllowedException;
import com.erich.dev.repository.ClientRepository;
import com.erich.dev.repository.RoleRepository;
import com.erich.dev.security.CustomUserServiceImpl;
import com.erich.dev.security.jwt.JwtTokenProvider;
import com.erich.dev.service.ClientService;
import com.erich.dev.util.validation.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepo;

    private final AccountServiceImpl accountService;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepo;
    private final ObjectsValidator<UsuarioDto> validator;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final CustomUserServiceImpl customUserService;

    @Override
    public UsuarioDto save(UsuarioDto client) {
        if (clientRepo.existsByEmail(client.getEmail())) {
            throw new OperationNotAllowedException("Email ya existe!");
        }
        Usuario usuario = UsuarioDto.toEntity(client);
        usuario.setPassword(passwordEncoder.encode(client.getPassword()));
        return UsuarioDto.fromEntity(clientRepo.save(usuario));
    }

    @Override
    public UsuarioDto update(UsuarioDto client, Long id) {
        if (!clientRepo.existsById(id)) {
            throw new IllegalArgumentException("ID NO EXISTE!");
        }
        return clientRepo.findById(id).map(x -> {
            x.setFirstName(client.getFirstName());
            x.setLastName(client.getLastName());
            x.setEmail(client.getEmail());
            return UsuarioDto.fromEntity(clientRepo.save(x));
        }).orElseThrow(() -> new EntityNotFoundException("Client no encontrado"));
    }

    @Override
    public UsuarioDto findById(Long id) {
        return clientRepo.findById(id).map(UsuarioDto::fromEntity).orElseThrow(() -> new EntityNotFoundException("Client no encontrado!"));
    }

    @Override
    public List<UsuarioDto> findAll() {
        return Streamable.of(clientRepo.findAll())
                .map(u -> UsuarioDto.fromEntity(u))
                .stream().toList();
    }

    public Iterable<Usuario> findAllUsuario() {
        return clientRepo.findAll();
    }

    @Transactional
    public Long validateAccount(Long userId) {
        Usuario usuario = clientRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("No se ha encontrado ningún usuario para la validación de la cuenta de usuario"));
        AccountDto account = AccountDto.builder().user(UsuarioDto.fromEntity(usuario)).build();
        var saveAccount = accountService.save(account);
        usuario.setAccount(Account.builder()
                .id(saveAccount.getId())
                .build());
        usuario.setActive(true);
        clientRepo.save(usuario);
        return usuario.getId();
    }

    @Transactional
    public Long invalidateAccount(Long id) {
        Usuario user = clientRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado ningún usuario para la validación de la cuenta de usuario"));
        user.setActive(false);
        clientRepo.save(user);
        return user.getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            return;
        }
        clientRepo.deleteById(id);
    }

    public JwtResponse login(LoginRequest loginRequest) throws Exception {
        this.auth(loginRequest.username(), loginRequest.password());
        UserDetails userDetails = customUserService.loadUserByUsername(loginRequest.username());
        return JwtResponse.builder()
                .accessToken(jwtTokenProvider.generateToken(userDetails))
                .TokenType("Bearer ")
                .build();
    }

    @Transactional
    public String register(SignupRequest signupRequest) {
        validationFieldSignup(signupRequest);
        Usuario u = Usuario.builder()
                .firstName(signupRequest.firstName())
                .lastName(signupRequest.lastName())
                .userName(signupRequest.userName())
                .email(signupRequest.email())
                .password(passwordEncoder.encode(signupRequest.password()))
                .repeatPassword(passwordEncoder.encode(signupRequest.repeatPassword()))
                .age(signupRequest.age())
                .build();

        validateRepeatPassword(signupRequest);
        validateRole(signupRequest, u);
        clientRepo.save(u);
        return "Usuario registrado con exito!";
    }

    private void validateRole(SignupRequest signupRequest, Usuario u) {
        Set<Role> roles = new HashSet<>();
        if (signupRequest.userName().contains("administrador")) {
            Role roleAdmin = roleRepo.findByAuthority("ROLE_ADMIN").orElseThrow(() -> new EntityNotFoundException("Role user no encontrado!"));
            roles.add(roleAdmin);
            u.setRoles(roles);
        } else {
            Role userRole = roleRepo.findByAuthority("ROLE_USER").orElseThrow(() -> new EntityNotFoundException("Role user no encontrado!"));
            roles.add(userRole);
            u.setRoles(roles);
        }
    }
    private void auth(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new DisabledException(e.getMessage());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("UserName o password Incorrect");
        }
    }

    private void validationFieldSignup(SignupRequest request) {
        if (clientRepo.existsByEmail(request.email())) {
            throw new OperationNotAllowedException("Error:  " + request.email() + " ya esta en uso!");
        }
        if (clientRepo.existsByUserName(request.userName())) {
            throw new OperationNotAllowedException("Error:  " + request.userName() + " ya esta en uso!");
        }
    }

    private void validateRepeatPassword(SignupRequest signupRequest) {
        if (!signupRequest.password().equals(signupRequest.repeatPassword())) {
            throw new OperationNotAllowedException("passwords no son iguales");
        }
    }
}
