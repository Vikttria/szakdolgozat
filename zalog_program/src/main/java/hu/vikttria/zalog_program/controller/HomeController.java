package hu.vikttria.zalog_program.controller;

import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import hu.vikttria.zalog_program.zaloghaz.Zalogjegy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String home(){
        return "bejelentkezes";
    }

    @RequestMapping("/felvet")
    public String felvet(Model model){
        model.addAttribute("zalogjegy", new Zalogjegy());
        return "felvet";
    }

    @RequestMapping(value = "/fel", method = RequestMethod.POST)
    public String felvetSubmit(@ModelAttribute Zalogjegy zalogjegy){
        log.info(zalogjegy.getLeiras());
        log.info(String.valueOf(zalogjegy.getDbSzam()));
        log.info(String.valueOf(zalogjegy.getKarat()));
        log.info(String.valueOf(zalogjegy.getOsszeg()));
        log.info(String.valueOf(zalogjegy.getSuly()));
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

    @RequestMapping("/ugyfelFelvet")
    public String ugyfelFelvet(Model model){
        model.addAttribute("ugyfel", new Ugyfel());
        return "ugyfelFelvet";
    }

    @RequestMapping(value = "/ugyfelUj", method = RequestMethod.POST)
    public String ugyfelFelvetSubmit(@ModelAttribute Ugyfel ugyfel){
        log.info(ugyfel.getNev());
        log.info(ugyfel.getAnyjaNeve());
        log.info(ugyfel.getCim());
        log.info(ugyfel.getEmail());
        log.info(ugyfel.getSzig());

        return "ugyfelFelvet";
    }

}
