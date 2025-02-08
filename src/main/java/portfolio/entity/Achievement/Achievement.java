package portfolio.entity.Achievement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import portfolio.entity.AchievementType.AchievementType;
import portfolio.entity.Technology.Technology;
import portfolio.entity.Visual.Visual;

@Entity
@Table(name = "Achievements")
public class Achievement 
{
    
    public Achievement() {
    }

    public Achievement(String name, String description, AchievementType type, List<Technology> technologies) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.technologies = technologies;
    }

    public Achievement(Long id, String name, String description, AchievementType type, List<Technology> technologies) 
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.technologies = technologies;
    }
    @OneToMany(mappedBy = "id.achievement")
    private List<Visual> visuals;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public Achievement(Long id, String name, String description, String gitHubLink, AchievementType type,
            List<Technology> technologies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.gitHubLink = gitHubLink;
        this.type = type;
        this.technologies = technologies;
    }

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = true)
    private String gitHubLink;
    @ManyToOne(cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "type_id", nullable = false, unique = false)
    @JsonManagedReference
    private AchievementType type;

    @ManyToMany(cascade = CascadeType.PERSIST) 
    @JoinTable(name = "achievements_technologies", 
    joinColumns = @JoinColumn(name = "achie_id"), inverseJoinColumns = @JoinColumn(name = "tech_id"))
    @JsonManagedReference
    private List<Technology> technologies = new ArrayList<Technology>();

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

    public AchievementType getType() {
        return type;
    }

    public void setType(AchievementType type) {
        this.type = type;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }
    
}
