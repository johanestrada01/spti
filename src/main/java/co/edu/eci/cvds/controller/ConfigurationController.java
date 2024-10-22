package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.model.Configuration;
import co.edu.eci.cvds.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping("/example")
    public String example(Model model) {
        model.addAttribute("premio", configurationService.getPremio());
        return "example";
    }

    @GetMapping("/example/api")
    public String exampleApi() {
        return "example-api";
    }

    @GetMapping("/example/api/configurations")
    @ResponseBody
    public List<Configuration> exampleApiConfigurations() {
        return configurationService.getAllConfigurations();
    }

    @PostMapping("/example/api/configurations")
    @ResponseBody
    public List<Configuration> exampleApiConfigurations(@RequestBody Configuration configuration) {
        configurationService.addConfiguration(configuration);
        return configurationService.getAllConfigurations();
    }
}
