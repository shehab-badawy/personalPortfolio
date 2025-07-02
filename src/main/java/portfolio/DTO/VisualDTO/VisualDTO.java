package portfolio.DTO.VisualDTO;

public class VisualDTO {
    private String visualLink;
    private String description;

    public VisualDTO() {}

    public VisualDTO(String visualLink, String description) {
        this.visualLink = visualLink;
        this.description = description;
    }

    public String getVisualLink() {
        return visualLink;
    }

    public void setVisualLink(String visualLink) {
        this.visualLink = visualLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
