package portfolio.service.AchievementTypeService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portfolio.entity.AchievementType.AchievementType;
import portfolio.repository.AchievementTypeRepo.AchievementTypeRepository;

@Service
public class AchievementTypeService 
{
    @Autowired
    AchievementTypeRepository achievementTypeRepository;
    public AchievementType createAchievementType(String typeName) {
        return achievementTypeRepository.findByName(typeName)
            .orElseGet(() -> {
                AchievementType newType = new AchievementType(typeName);
                return achievementTypeRepository.save(newType);
            });
    }

    public List<AchievementType> getAllAchievementTypes()
    {
        return (List<AchievementType>) achievementTypeRepository.findAll();
    }
    public Optional<AchievementType> getAchievementTypeByName(String name)
    {
        return  achievementTypeRepository.findByName(name);
    }
}
