package portfolio.service.AchievementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;
import portfolio.DTO.AchievementDTO.AchievementCreateDTO;
import portfolio.DTO.AchievementDTO.AchievementPatchDTO;
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
    public Achievement getAchievementById(Long id)
    {
        return achievementRepository.findById(id).get();
    }
    public void saveAchievement(Achievement achievement)
    {
        achievementRepository.save(achievement);
    }
    public void deleteAchievementById(Long id) {
    Achievement achievement = achievementRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Achievement not found with ID " + id));

    // Optional: Delete associated visuals first if cascading isn't set up
    // visualRepository.deleteById_Achievement(achievement);

    // Delete achievement itself
    achievementRepository.delete(achievement);
}

     public Achievement createAchievementFromDTO(AchievementCreateDTO dto) {
        // Resolve AchievementType by name
        System.out.println(dto.getType());
        AchievementType type = achievementTypeService.getAchievementTypeByName(dto.getType())
            .orElseGet(() -> achievementTypeService.createAchievementType(dto.getType()));

    List<Technology> technologies = dto.getTechnologies().stream()
        .map(technologyService::createTechnology)
        .collect(Collectors.toList());
        System.out.println("yaaaay!--------------------------------------------------------------------------------");
        // Create and save Achievement
        Achievement achievement = new Achievement();
        achievement.setName(dto.getName());
        achievement.setDescription(dto.getDescription());
        achievement.setGitHubLink(dto.getGitHubLink());
        achievement.setType(type);
        achievement.setTechnologies(technologies);

        return achievementRepository.save(achievement);
    }
    public Optional<Achievement> getAchievementByName(String name)
    {
        return achievementRepository.findByName(name);
    }
    public Achievement updateAchievementFromDTO(Long id, AchievementPatchDTO dto, Achievement achievement) 
    {
        if(dto.getName() != null)
            achievement.setName(dto.getName());
        if(dto.getDescription() != null)
            achievement.setDescription(dto.getDescription());
        if(dto.getGitHubLink() != null)
            achievement.setGitHubLink(dto.getGitHubLink());
        if (dto.getTypeName() != null) 
        {
            AchievementType type = achievementTypeService.getAchievementTypeByName(dto.getTypeName())
            .orElseGet(() -> achievementTypeService.createAchievementType(dto.getTypeName()));
            achievement.setType(type);
        }
        if (dto.getTechnologyNames() != null) 
        {
        List<Technology> technologies = dto.getTechnologyNames().stream()
            .map(technologyService::createTechnology)
            .collect(Collectors.toList());
            achievement.setTechnologies(technologies);
        }
        return achievementRepository.save(achievement);
    }

}
