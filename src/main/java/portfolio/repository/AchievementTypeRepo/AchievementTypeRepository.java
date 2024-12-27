package portfolio.repository.AchievementTypeRepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import portfolio.entity.AchievementType.AchievementType;
@Repository
public interface AchievementTypeRepository extends CrudRepository <AchievementType, Long>
{
 public AchievementType findByName(String name);   
}
