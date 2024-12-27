package portfolio.entity.AchievementType;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import portfolio.entity.Achievement.Achievement;

@Entity
@Table(name = "Achievement_Types")
public class AchievementType 
{
    public AchievementType(String name) {
        this.name = name;
    }

    public AchievementType() {
    }

    public AchievementType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    @JsonBackReference
    List<Achievement> achievements = new ArrayList<Achievement>();

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

}
