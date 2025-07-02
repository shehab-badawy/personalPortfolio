package portfolio.DTO.AchievementDTO;

import java.util.List;

public class AchievementPatchDTO {
    private String name;
    private String description;
    private String gitHubLink;
    private String typeName;
    private List<String> technologyNames;
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
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public List<String> getTechnologyNames() {
        return technologyNames;
    }
    public void setTechnologyNames(List<String> technologyNames) {
        this.technologyNames = technologyNames;
    }
    
}
