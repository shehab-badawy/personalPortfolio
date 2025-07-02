package portfolio.service.VisualService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import portfolio.entity.Achievement.Achievement;
import portfolio.entity.Visual.Visual;
import portfolio.repository.VisualRepository.VisualRepository;

@Service
public class VisualService {
    @Value("${app.base-url}")
    private String baseUrl;

    @Autowired
    VisualRepository visualRepository;

    public List<Visual> findVisualsByAchievement(Achievement achievement) {
        List<Visual> visuals = visualRepository.findById_Achievement(achievement);

        for (Visual visual : visuals) {
            String visualLink = visual.getId().getVisualLink();

            if (visualLink.startsWith("server")) {
                String link = baseUrl + "/images/files/" + visualLink.substring(7);
                visual.getId().setVisualLink(link);
            } else if (isYoutubeLink(visualLink)) {
                String embed = toYoutubeEmbed(visualLink);
                visual.getId().setVisualLink(embed);
            }
        }

        return visuals;
    }

    public void saveVisual(Visual visual) {
        visualRepository.save(visual);
    }

    private boolean isYoutubeLink(String link) {
        return link.contains("youtube.com") || link.contains("youtu.be");
    }

    private String toYoutubeEmbed(String url) {
        try {
            if (url.contains("youtube.com/watch?v=")) {
                String id = url.substring(url.indexOf("v=") + 2);
                int amp = id.indexOf("&");
                if (amp != -1) id = id.substring(0, amp);
                return "https://www.youtube.com/embed/" + id;
            } else if (url.contains("youtu.be/")) {
                String id = url.substring(url.indexOf("youtu.be/") + 9);
                int slash = id.indexOf('/');
                if (slash != -1) id = id.substring(0, slash);
                return "https://www.youtube.com/embed/" + id;
            }
        } catch (Exception e) {
            // fallback silently
        }
        return url;
    }
}
