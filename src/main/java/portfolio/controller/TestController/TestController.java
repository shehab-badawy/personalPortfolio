package portfolio.controller.TestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import portfolio.DTO.AchievementDTO.AchievementCreateDTO;
import portfolio.DTO.AchievementDTO.AchievementPatchDTO;
import portfolio.DTO.AchievementDTO.AchievementResponseDTO;
import portfolio.DTO.VisualDTO.VisualDTO;
import portfolio.Security.JwtUtil;
import portfolio.entity.Achievement.Achievement;
import portfolio.entity.AchievementType.AchievementType;
import portfolio.entity.Admin.Admin;
import portfolio.entity.Technology.Technology;
import portfolio.entity.Visual.Visual;
import portfolio.repository.AdminRepository.AdminRepository;
import portfolio.service.AchievementService.AchievementService;
import portfolio.service.AchievementTypeService.AchievementTypeService;
import portfolio.service.TechnologyService.TechnologyService;
import portfolio.service.VisualService.VisualService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private AchievementResponseDTO mapToResponseDTO(Achievement achievement) {
    AchievementResponseDTO dto = new AchievementResponseDTO();
    dto.setName(achievement.getName());
    dto.setDescription(achievement.getDescription());
    dto.setGitHubLink(achievement.getGitHubLink());
    dto.setTypeName(achievement.getType().getName());
    dto.setTechnologyNames(achievement.getTechnologies().stream()
            .map(Technology::getName)
            .collect(Collectors.toList()));
    dto.setVisuals(achievement.getVisuals().stream()
            .map(v -> new VisualDTO(v.getId().getVisualLink(), v.getDescription()))
            .collect(Collectors.toList()));
    return dto;
}

    @GetMapping(value = "/types")
    public List<AchievementType> test()
    {
        return achievementTypeService.getAllAchievementTypes();
    }
    @GetMapping("/achies")
    public List<Achievement> test2()
    {
        System.out.println("test");
        return achievementService.getAllAchievements();
    } 
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public String re()
    {
        return "hi";
    } 

    @PostMapping("/login")
    public String res(@RequestBody String json)
    {
        try
        {
            System.out.println(json);
            JsonNode rootNode = objectMapper.readTree(json);
            try
            {
                String username = rootNode.get("username").asText();
                String password = rootNode.get("password").asText();
                Optional<Admin> admin = adminRepository.findByUsername(username);
                if(admin != null)
                {
                    System.out.println("admin is found");
                    boolean condition = passwordEncoder.matches(password, admin.get().getPassword());
                    if(condition)
                    {
                        System.out.println("before");
                        System.out.println(JwtUtil.generateToken(username));
                        return JwtUtil.generateToken(username);
                    }
                    System.out.println("wrong pass");
                }
                return "wrong credintials!";
            } 
            catch(Exception e){
                return e.getMessage();
            }
        }catch(Exception e){
            return e.getMessage();
        }
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
    public ResponseEntity<AchievementResponseDTO> createAchievement(@RequestBody AchievementCreateDTO dto) {
        // 1️⃣ Check if achievement already exists by name
        Optional<Achievement> existingAchievement = achievementService.getAchievementByName(dto.getName());
        if (existingAchievement.isPresent()) {
            // Already exists: return mapped response
            return ResponseEntity.ok(mapToResponseDTO(existingAchievement.get()));
        }

        // 2️⃣ If not found, create a new achievement
        Achievement newAchievement = achievementService.createAchievementFromDTO(dto);
        AchievementResponseDTO responseDTO = mapToResponseDTO(newAchievement);
        return ResponseEntity.ok(responseDTO);
    }

        @GetMapping("/achies/{id}")
    public ResponseEntity<AchievementResponseDTO> getAchievementById(@PathVariable Long id) {
        Achievement achievement = achievementService.getAchievementById(id);
        if (achievement != null) {
            AchievementResponseDTO responseDTO = mapToResponseDTO(achievement);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // @PutMapping("/achies/{id}")
    @PatchMapping("/achies/{id}")
    public ResponseEntity<AchievementResponseDTO> updateAchievement(
            @PathVariable Long id,
            @RequestBody AchievementPatchDTO dto) {
        Achievement existing = achievementService.getAchievementById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
       
        Achievement updated = achievementService.updateAchievementFromDTO(id, dto, existing);
        AchievementResponseDTO responseDTO = mapToResponseDTO(updated);
        return ResponseEntity.ok(responseDTO);
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
