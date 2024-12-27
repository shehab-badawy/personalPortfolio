package portfolio.service.TechnologyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portfolio.entity.Technology.Technology;
import portfolio.repository.TechnologyRepo.TechnologyRepository;

@Service
public class TechnologyService 
{
    @Autowired
    TechnologyRepository technologyRepository;   
    public Technology createTechnology(String technology)
    {
        Technology technology2 = technologyRepository.findByName(technology);
        if(technology2 == null)
        {
            technology2 = new Technology(technology);
            technologyRepository.save(technology2);
            return technology2;
        }
        return technology2;
    }
    public List<Technology> getAllTechnolgoies()
    {
        return (List<Technology>) technologyRepository.findAll();
    }
}
