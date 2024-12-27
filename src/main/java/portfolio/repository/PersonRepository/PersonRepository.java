package portfolio.repository.PersonRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import portfolio.entity.Person.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>
{   
}
