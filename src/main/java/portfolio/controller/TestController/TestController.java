package portfolio.controller.TestController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import portfolio.entity.Achievement.Achievement;
import portfolio.entity.AchievementType.AchievementType;
import portfolio.entity.Technology.Technology;
import portfolio.entity.Visual.Visual;
import portfolio.service.AchievementService.AchievementService;
import portfolio.service.AchievementTypeService.AchievementTypeService;
import portfolio.service.TechnologyService.TechnologyService;
import portfolio.service.VisualService.VisualService;

@RestController
@RequestMapping("/test")
public class TestController 
{
    public ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    AchievementTypeService achievementTypeService;
    @Autowired
    AchievementService achievementService;
    @Autowired
    TechnologyService technologyService;
    @Autowired
    VisualService visualService;
    @GetMapping(value = "/types")
    public List<AchievementType> test()
    {
        return achievementTypeService.getAllAchievementTypes();
    }
    @GetMapping("/achies")
    public List<Achievement> test2()
    {
        return achievementService.getAllAchievements();
    } 
    @GetMapping("/techs")
    public List<Technology> test3()
    {
        return technologyService.getAllTechnolgoies();
    }
    @GetMapping("/visuals/{id}")
    public List<Visual> test10(@PathVariable Long id)
    {
        Achievement achievement = achievementService.getAchievementById(id);
        return visualService.findVisualsByAchievement(achievement);
    }
    @PostMapping("/achies")
    public Achievement test4(@RequestBody String rawJson) throws JsonMappingException, JsonProcessingException
    {
        return achievementService.createAchievemnt(rawJson);
    }
    @PostMapping("/techs")
    public String test5(@RequestBody String technology) throws JsonMappingException, JsonProcessingException
    {
        JsonNode rootNode = objectMapper.readTree(technology);
        try
        {
            String tech = rootNode.get("name").asText();
            return "great";
        } 
        catch(Exception e){}
        return "failed to do so";
    }
    @PostMapping("/type")
    public AchievementType test6(@RequestBody AchievementType achievementType)
    {
        return achievementTypeService.createAchievementType(achievementType.getName());
    }
}
