package portfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import portfolio.entity.AchievementType.AchievementType;
import portfolio.service.AchievementTypeService.AchievementTypeService;


@RestController
@RequestMapping("/api")

public class AchievementsTypeController {
    @Autowired
    AchievementTypeService achievementTypeService;
    
    @GetMapping("/types")
    public List<AchievementType> getAllTypes()
    {
        return achievementTypeService.getAllAchievementTypes();
    }
}
