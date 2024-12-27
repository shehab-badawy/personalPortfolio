package portfolio.entity.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import portfolio.entity.Person.Person;

@Entity
@Table(name = "Posts")
public class Post 
{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String descreption;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public Post(String desc, Person person) {
        this.descreption = desc;
        this.person = person;
    }
    public Post(){}
    
    public Post(String desc) {
        this.descreption = desc;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDesc() {
        return descreption;
    }
    public void setDesc(String desc) {
        this.descreption = desc;
    }
        
}
