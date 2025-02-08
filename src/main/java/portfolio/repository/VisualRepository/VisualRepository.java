package portfolio.repository.VisualRepository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import portfolio.entity.Achievement.Achievement;
import portfolio.entity.Visual.Visual;
import portfolio.entity.VisualKey.VisualKey;

public interface VisualRepository extends CrudRepository<Visual, VisualKey>
{
    List<Visual> findById_Achievement(Achievement achievement);
}
