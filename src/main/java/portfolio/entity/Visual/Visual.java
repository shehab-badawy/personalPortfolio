package portfolio.entity.Visual;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import portfolio.entity.VisualKey.VisualKey;

@Entity
@Table(name = "visuals")
public class Visual 
{
    public Visual() 
    {
    }
    public Visual(VisualKey id, String description)
    {
        this.id = id;
        this.description = description;
    }
    // composite primary key consist of visual link and achievenemnt id
    @EmbeddedId
    private VisualKey id;
    private String description;
    public VisualKey getId() {
        return id;
    }
    public void setId(VisualKey id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
