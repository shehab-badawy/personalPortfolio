package portfolio.controller;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import jakarta.persistence.EntityNotFoundException;
import portfolio.DTO.AchievementDTO.AchievementCreateDTO;
import portfolio.DTO.AchievementDTO.AchievementPatchDTO;
import portfolio.DTO.AchievementDTO.AchievementResponseDTO;
import portfolio.DTO.AchievementTypeDTO.AchievementTypeDTO;
import portfolio.DTO.TechnologyDTO.TechnologyDTO;
import portfolio.DTO.VisualDTO.VisualDTO;
import portfolio.Security.JwtUtil;
import portfolio.entity.Achievement.Achievement;
import portfolio.entity.AchievementType.AchievementType;
import portfolio.entity.Admin.Admin;
import portfolio.entity.Technology.Technology;
import portfolio.entity.Visual.Visual;
import portfolio.entity.VisualKey.VisualKey;
import portfolio.repository.AchievementTypeRepo.AchievementTypeRepository;
import portfolio.repository.AdminRepository.AdminRepository;
import portfolio.repository.TechnologyRepo.TechnologyRepository;
import portfolio.service.AchievementService.AchievementService;
import portfolio.service.AchievementTypeService.AchievementTypeService;
import portfolio.service.TechnologyService.TechnologyService;
import portfolio.service.VisualService.VisualService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/admin")

public class AdminController 
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
    @Autowired
    TechnologyRepository technologyRepository;

    @Autowired
    AchievementTypeRepository achievementTypeRepository;
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
    @PatchMapping("/achievements/{id}")
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
    @PostMapping("/technologies")
    public ResponseEntity<?> addTechnology(@RequestBody TechnologyDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Technology name cannot be empty");
        }
        technologyService.createTechnology(dto.getName());
        return ResponseEntity.ok().build();
    }
    @PutMapping("/technologies/{id}")
    public ResponseEntity<?> updateTechnology(@PathVariable Long id, @RequestBody TechnologyDTO dto) {
        Technology tech = technologyRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Technology not found"));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            tech.setName(dto.getName());
            technologyRepository.save(tech);
        }

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/achievements/{achievementId}/technologies/{techName}")
    public ResponseEntity<?> removeTechnologyFromAchievement(
        @PathVariable Long achievementId,
        @PathVariable String techName) {

        Achievement achievement = achievementService.getAchievementById(achievementId);
        Technology tech = technologyRepository.findByName(techName)
            .orElseThrow(() -> new EntityNotFoundException("Tech not found"));

        achievement.getTechnologies().remove(tech);
        achievementService.saveAchievement(achievement); // Save updated achievement

        return ResponseEntity.ok().build();
    }

    @PostMapping("/types")
    public AchievementType createAchievementType(@RequestBody AchievementTypeDTO achievementType)
    {
        return achievementTypeService.createAchievementType(achievementType.getName());
    }

    @PutMapping("/types/{id}")
    public ResponseEntity<?> updateType(@PathVariable Long id, @RequestBody AchievementTypeDTO dto) {
        AchievementType type = achievementTypeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Type not found"));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            type.setName(dto.getName());
            achievementTypeRepository.save(type);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/achievements/{id}/youtubelink")
    public ResponseEntity<?> addYoutubeLink(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String link = body.get("link");
        Achievement achievement = achievementService.getAchievementById(id);
        Visual visual = new Visual(new VisualKey(achievement, link), "YouTube Video");
        visualService.saveVisual(visual);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<?> deleteAchievement(@PathVariable Long id) {
        try {
            achievementService.deleteAchievementById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();  // 404 if not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete achievement.");
        }
    }


}
