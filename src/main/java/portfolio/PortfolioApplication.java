package portfolio;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import portfolio.Upload.Storage.StorageProperties;
import portfolio.entity.Achievement.Achievement;
import portfolio.entity.AchievementType.AchievementType;
import portfolio.entity.Admin.Admin;
import portfolio.entity.Person.Person;
import portfolio.entity.Post.Post;
import portfolio.entity.Technology.Technology;
import portfolio.entity.Visual.Visual;
import portfolio.entity.VisualKey.VisualKey;
import portfolio.repository.AchievementRepo.AchievementRepository;
import portfolio.repository.AchievementTypeRepo.AchievementTypeRepository;
import portfolio.repository.AdminRepository.AdminRepository;
import portfolio.repository.PersonRepository.PersonRepository;
import portfolio.repository.PostRepo.PostRepository;
import portfolio.repository.TechnologyRepo.TechnologyRepository;
import portfolio.repository.VisualRepository.VisualRepository;
import portfolio.service.VisualService.VisualService;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)

public class PortfolioApplication implements CommandLineRunner {



    @Autowired 
    AchievementRepository achievementRepository;
    @Autowired 
    TechnologyRepository technologyRepository;
    @Autowired 
    AchievementTypeRepository achievementTypeRepository;
    @Autowired
    VisualRepository visualRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    PortfolioApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public static void main(String[] args) 
    {
        SpringApplication.run(PortfolioApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception 
    {
        // TODO don't forget to make .env file and make a function that created admin user
        // String pass = passwordEncoder.encode("12345"); 
        // Admin admin = new Admin("test1", pass);
        // adminRepository.save(admin);
    }
}
