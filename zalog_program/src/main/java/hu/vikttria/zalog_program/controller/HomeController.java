package hu.vikttria.zalog_program.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "bejelentkezes";
    }

    @RequestMapping("/felvet")
    public String felvet(){
        return "felvet";
    }

    @RequestMapping("/kivalt")
    public String kivalt(){
        return "kivalt";
    }

    @RequestMapping("/hosszabbit")
    public String hosszabbit(){
        return "hosszabbit";
    }

    @RequestMapping("/kereses")
    public String kereses(){
        return "kereses";
    }

    @RequestMapping("/bejelentkezes")
    public String bejelentkezes(){
        return "bejelentkezes";
    }

}
