package portfolio.repository.PostRepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import portfolio.entity.Post.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>
{
    
}
