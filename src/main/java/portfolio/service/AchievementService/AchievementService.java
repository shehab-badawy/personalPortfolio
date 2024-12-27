package portfolio.service.AchievementService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import portfolio.entity.Achievement.Achievement;
import portfolio.entity.AchievementType.AchievementType;
import portfolio.entity.Technology.Technology;
import portfolio.repository.AchievementRepo.AchievementRepository;
import portfolio.service.AchievementTypeService.AchievementTypeService;
import portfolio.service.TechnologyService.TechnologyService;

@Service
public class AchievementService 
{
    @Autowired
    AchievementRepository achievementRepository;
    @Autowired
    AchievementTypeService achievementTypeService;
    @Autowired
    TechnologyService technologyService;
    private ObjectMapper objectMapper = new ObjectMapper(null);
    public List<Achievement> getAllAchievements()
    {
        return (List<Achievement>)achievementRepository.findAll();
    }
    public Achievement createAchievemnt(String achievementJson) throws JsonMappingException, JsonProcessingException
    {
        JsonNode rootNode = objectMapper.readTree(achievementJson);
        String name = rootNode.get("name").asText();
        String description = rootNode.get("description").asText();
        String gitHubLink = rootNode.get("gitHubLink").asText(null);
        JsonNode typeNode = rootNode.get("type");
        List<Technology> technologies = new ArrayList<Technology>();
        Technology technology;
        AchievementType type = achievementTypeService.createAchievementType(typeNode.get("name").asText());
        JsonNode techsNode = rootNode.get("technologies");
        if(techsNode.isArray() && techsNode != null)
        {
            for (JsonNode techNode : techsNode) 
            {
                technology = technologyService.createTechnology(techNode.get("name").asText());
                technologies.add(technology);
            }
        }
        Achievement achievement = new Achievement(name, description, type, technologies);
        return achievementRepository.save(achievement);
    }
    
}
