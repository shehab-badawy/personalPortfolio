package portfolio.entity.VisualKey;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import portfolio.entity.Achievement.Achievement;

@Embeddable
public class VisualKey implements Serializable
{
    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;
    private String visualLink;
    public VisualKey(){}
    public VisualKey(Achievement achievement, String visualLink) 
    {
        this.achievement = achievement;
        this.visualLink = visualLink;
    }
    public Achievement getAchievement() {
        return achievement;
    }
    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }
    public String getVisualLink() {
        return visualLink;
    }
    public void setVisualLink(String visualLink) {
        this.visualLink = visualLink;
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(achievement, visualLink);
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        VisualKey vk = (VisualKey) o;
        return Objects.equals(achievement, vk.achievement) &&
        Objects.equals(visualLink, vk.visualLink);       
    }

}
