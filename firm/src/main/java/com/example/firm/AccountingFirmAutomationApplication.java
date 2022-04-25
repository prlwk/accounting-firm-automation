package com.example.firm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.firm.repository.RoleRepository;
import com.example.firm.repository.UserRepository;

@SpringBootApplication
public class AccountingFirmAutomationApplication implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public AccountingFirmAutomationApplication(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountingFirmAutomationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //roleRepository.saveAll(Set.of(new Role("ROLE_USER"), new Role("ROLE_ADMIN")));
        //userRepository.save(new User("admin", "admin", Set.of(roleRepository.findRoleByName("ROLE_ADMIN").get())));
    }
}
