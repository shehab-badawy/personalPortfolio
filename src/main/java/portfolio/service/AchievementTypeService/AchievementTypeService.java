package portfolio.service.AchievementTypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portfolio.entity.AchievementType.AchievementType;
import portfolio.repository.AchievementTypeRepo.AchievementTypeRepository;

@Service
public class AchievementTypeService 
{
    @Autowired
    AchievementTypeRepository achievementTypeRepository;
    public AchievementType createAchievementType(String type)
    {
        AchievementType achievementType = achievementTypeRepository.findByName(type);
        if(achievementType == null)
        {
            achievementType = new AchievementType(type);
            achievementTypeRepository.save(achievementType);
        }
        return achievementType;
    }
    public List<AchievementType> getAllAchievementTypes()
    {
        return (List<AchievementType>) achievementTypeRepository.findAll();
    }
}
