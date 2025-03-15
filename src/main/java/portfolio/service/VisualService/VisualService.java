package portfolio.service.VisualService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import portfolio.entity.Achievement.Achievement;
import portfolio.entity.Visual.Visual;
import portfolio.repository.VisualRepository.VisualRepository;

@Service
public class VisualService 
{
    @Value("${app.base-url}")
    private String baseUrl;
    @Autowired
    VisualRepository visualRepository;
    public List<Visual> findVisualsByAchievement(Achievement achievement)
    {
        List<Visual> visuals = visualRepository.findById_Achievement(achievement);
        for (Visual visual : visuals) 
        {
            String visualLink = visual.getId().getVisualLink(); 
            if(visualLink.startsWith("server"))
            {
                String link = baseUrl + "/upload/files/" + visualLink.substring(7);
                visual.getId().setVisualLink(link);
            }    
        }
        return visuals;
    }
    public void saveVisual(Visual visual)
    {
        visualRepository.save(visual);
    }
}
