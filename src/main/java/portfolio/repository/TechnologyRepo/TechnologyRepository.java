package portfolio.repository.TechnologyRepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import portfolio.entity.Technology.Technology;

@Repository
public interface TechnologyRepository extends CrudRepository<Technology, Long>
{
    public Technology findByName(String name);
}
