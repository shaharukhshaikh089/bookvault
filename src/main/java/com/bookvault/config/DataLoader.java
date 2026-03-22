package com.bookvault.config;

import com.bookvault.entity.AppUser;
import com.bookvault.enums.Role;
import com.bookvault.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadUsers(AppUserRepository repo) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            repo.findByEmail("librarian@example.com").ifPresent(repo::delete);
            repo.findByEmail("member@example.com").ifPresent(repo::delete);

            AppUser librarian = new AppUser();
            librarian.setEmail("librarian@example.com");
            librarian.setPassword(encoder.encode("password"));
            librarian.setRole(Role.LIBRARIAN);
            repo.save(librarian);

            AppUser member = new AppUser();
            member.setEmail("member@example.com");
            member.setPassword(encoder.encode("password"));
            member.setRole(Role.MEMBER);
            repo.save(member);
        };
    }
}