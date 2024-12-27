package portfolio.entity.Person;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import portfolio.entity.Post.Post;

@Entity
@Table(name = "persons")
public class Person 
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<Post>();

    public void addPost(Post post)
    {
        posts.add(post);
    }
    public List<Post> getPosts() {
        return posts;
    }
    public Person(){}
    public Person(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
    }
    public Long getId() {
        return id;
    }
}

