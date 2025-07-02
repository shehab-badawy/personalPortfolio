package portfolio.service.TechnologyService;

import java.util.List;
import java.util.Optional;

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
        return technologyRepository.findByName(technology).orElseGet(() -> {
            Technology newTech = new Technology(technology);
            return technologyRepository.save(newTech);
        });

    }
    public List<Technology> getAllTechnolgoies()
    {
        return (List<Technology>) technologyRepository.findAll();
    }
    public Optional<Technology> getTechonlogyByName(String name)
    {
        return  technologyRepository.findByName(name);
    }
}
