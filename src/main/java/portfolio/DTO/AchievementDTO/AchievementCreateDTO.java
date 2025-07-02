package portfolio.DTO.AchievementDTO;
import java.util.List;

//@ TODO: don't forget to pur annotations
public class AchievementCreateDTO {
    private Long id;
    private String name;
    private String description;
    private String gitHubLink;
    private String type;
    private List<String> technologies;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getGitHubLink() {
        return gitHubLink;
    }
    public void setGitHubLink(String gitHubLink) {
        this.gitHubLink = gitHubLink;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getTechnologies() {
        return technologies;
    }
    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }
}
