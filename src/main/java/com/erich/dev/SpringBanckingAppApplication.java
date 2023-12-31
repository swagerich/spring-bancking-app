package com.erich.dev;

import com.erich.dev.entity.Role;
import com.erich.dev.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBanckingAppApplication implements CommandLineRunner {
    private final RoleRepository roleRepo;

    public SpringBanckingAppApplication(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBanckingAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Set<Role> roles = new HashSet<>();
        if (roleRepo.findByAuthority("ROLE_ADMIN").isEmpty()) {
            Role roleAdmin = Role.builder()
                    .authority("ROLE_ADMIN")
                    .build();
            roles.add(roleAdmin);
        }
        if (roleRepo.findByAuthority("ROLE_USER").isEmpty()) {
            Role roleUser = Role.builder()
                    .authority("ROLE_USER")
                    .build();
            roles.add(roleUser);
        }
        roleRepo.saveAll(roles);
    }
}
