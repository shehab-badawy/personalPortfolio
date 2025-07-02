package portfolio.configuration;



import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import portfolio.entity.Admin.Admin;
import portfolio.repository.AdminRepository.AdminRepository;

import java.util.Optional;

@Component
public class StartupRunner implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public StartupRunner(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Optional<Admin> adminExists = adminRepository.findByUsername("admin");
        if (adminExists.isEmpty()) {
            Admin admin = new Admin("admin", passwordEncoder.encode("admin123")); // Change password later
            adminRepository.save(admin);
            System.out.println("Admin account created!");
        }
    }
}
