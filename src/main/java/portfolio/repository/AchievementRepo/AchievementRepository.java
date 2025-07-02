package portfolio.repository.AchievementRepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import portfolio.entity.Achievement.Achievement;
import java.util.Optional;


@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long>
{
    public Optional<Achievement> findByName(String name);
}
