package portfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import portfolio.entity.Technology.Technology;
import portfolio.service.TechnologyService.TechnologyService;

@RestController
@RequestMapping("/api")
public class TechnologyController {
    @Autowired
    TechnologyService technologyService;
    @GetMapping("/technologies")
    public List<Technology> getAllTechnologies()
    {
        return technologyService.getAllTechnolgoies();
    }
}
