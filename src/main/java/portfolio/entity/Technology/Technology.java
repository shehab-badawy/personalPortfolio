package portfolio.entity.Technology;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import portfolio.entity.Achievement.Achievement;

@Entity
@Table(name = "technologies")
public class Technology 
{
    public Technology() {
    }
    public Technology(String name) {
        this.name = name;
    }
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public String name;

    @ManyToMany(mappedBy = "technologies")
    @JsonBackReference
    private List<Achievement> achievements = new ArrayList<Achievement>();
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
    public List<Achievement> getAchievements() {
        return achievements;
    }
    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }
    
}
