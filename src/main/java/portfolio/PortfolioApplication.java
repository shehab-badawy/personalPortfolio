package portfolio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import portfolio.entity.Achievement.Achievement;
import portfolio.entity.AchievementType.AchievementType;
import portfolio.entity.Person.Person;
import portfolio.entity.Post.Post;
import portfolio.entity.Technology.Technology;
import portfolio.repository.AchievementRepo.AchievementRepository;
import portfolio.repository.AchievementTypeRepo.AchievementTypeRepository;
import portfolio.repository.PersonRepository.PersonRepository;
import portfolio.repository.PostRepo.PostRepository;
import portfolio.repository.TechnologyRepo.TechnologyRepository;


@SpringBootApplication
@RestController
public class PortfolioApplication implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired 
    AchievementRepository achievementRepository;
    @Autowired 
    TechnologyRepository technologyRepository;
    @Autowired 
    AchievementTypeRepository achievementTypeRepository;

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }

    @GetMapping("/")
    String test()
    {
        return "hello";
    }
    @Transactional
    @Override
    public void run(String... args) throws Exception 
    {
        Technology t = new Technology("C");
        // technologyRepository.save(t);
        AchievementType achievementType = new AchievementType("Personal Project");
        // achievementTypeRepository.save(achievementType);
        List<Technology> ts = new ArrayList<Technology>();
        ts.add(t);
        Achievement achievement = new Achievement("detecting metal car", "it is a car that detects metals", achievementType, ts);
        achievementRepository.save(achievement);
        Long t_id = achievement.getTechnologies().get(0).getId();
        Optional<Technology> t1 = technologyRepository.findById(t_id);
        if(t1 != null)
        {
            System.out.println("yahoooo");
            System.out.println(t1.get().getName());
        }
        else
        {
            System.out.println("oh no");
        }
        achievement = new Achievement("Rov", "Remotely Operated Vehicle", achievementType, ts);
        achievementRepository.save(achievement);

    }
}
