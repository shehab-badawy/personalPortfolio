package portfolio.repository.TechnologyRepo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import portfolio.entity.Technology.Technology;

@Repository
public interface TechnologyRepository extends CrudRepository<Technology, Long>
{
    public Optional<Technology> findByName(String name);
}
