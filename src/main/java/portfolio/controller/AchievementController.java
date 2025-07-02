package portfolio.controller;

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
@RequestMapping("/api")
public class AchievementController 
{
    @Autowired
    AchievementService achievementService;

    private AchievementResponseDTO mapToResponseDTO(Achievement achievement) 
    {
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
    @GetMapping("/achievements")
    public List<Achievement> getAllAchievements()
    {
        System.out.println("getting all achievements");
        return achievementService.getAllAchievements();
    }
    @PostMapping("/achievements")
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

    @GetMapping("/achievements/{id}")
    public ResponseEntity<AchievementResponseDTO> getAchievementById(@PathVariable Long id) {
        Achievement achievement = achievementService.getAchievementById(id);
        if (achievement != null) {
            AchievementResponseDTO responseDTO = mapToResponseDTO(achievement);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
