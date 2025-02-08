package portfolio.service.VisualService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portfolio.entity.Achievement.Achievement;
import portfolio.entity.Visual.Visual;
import portfolio.repository.VisualRepository.VisualRepository;

@Service
public class VisualService 
{
    @Autowired
    VisualRepository visualRepository;
    public List<Visual> findVisualsByAchievement(Achievement achievement)
    {
        return visualRepository.findById_Achievement(achievement);
    }
    public void saveVisual(Visual visual)
    {
        visualRepository.save(visual);
    }
}
