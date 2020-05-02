package hu.vikttria.zalog_program.controller;

import hu.vikttria.zalog_program.service.UgyfelService;
import hu.vikttria.zalog_program.service.ZalogjegyService;
import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import hu.vikttria.zalog_program.zaloghaz.Zalogjegy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;

@Controller
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ZalogjegyService zalogjegyService;
    @Autowired
    public void setZalogjegyService(ZalogjegyService zalogjegyService) {
        this.zalogjegyService = zalogjegyService;
    }

    private UgyfelService ugyfelService;
    @Autowired
    public void setUgyfelService(UgyfelService ugyfelService) {
        this.ugyfelService = ugyfelService;
    }

    @RequestMapping("/")
    public String home(){
        return "bejelentkezes";
    }


    @RequestMapping("/felvet")
    public String felvet(Model model){
        model.addAttribute("zalogjegy", new Zalogjegy());
        model.addAttribute("ugyfelek", ugyfelService.allUgyfel());
        model.addAttribute("maiNap", LocalDate.now());
        model.addAttribute("lejarat", LocalDate.now().plusDays(90));
        return "felvet";
    }

    @RequestMapping(value = "/zalogUj", method = RequestMethod.POST)
    public String felvetSubmit(@ModelAttribute Zalogjegy zalogjegy, Model model){
        log.info("Új zálogjegy felvétele");

        zalogjegyService.ujZalog(zalogjegy.getLeiras(), zalogjegy.getKarat(), zalogjegy.getSuly(), zalogjegy.getDbSzam(), zalogjegy.getOsszeg(), LocalDate.now(), zalogjegy.getUgyfel());

        model.addAttribute("zalogjegy", new Zalogjegy());
        model.addAttribute("ugyfelek", ugyfelService.allUgyfel());
        model.addAttribute("maiNap", LocalDate.now());
        model.addAttribute("lejarat", zalogjegyService.futamidoLejarta(LocalDate.now()));

        return "felvet";
    }

    @RequestMapping("/kivalt")
    public String kivalt(Model model){
        model.addAttribute("zalogjegy", new Zalogjegy());

        return "kivalt";
    }

    @RequestMapping(value = "/kivalt", method = RequestMethod.POST, params = "action=ok")
    public String kivaltOkSubmit(@ModelAttribute Zalogjegy zalogjegy, Model model){
        log.info("kivált OK gomb");
        zalogjegy = zalogjegyService.getZalogjegy(zalogjegy.getId(), zalogjegy.getOsszeg());

        model.addAttribute("ugyfel", zalogjegy.getUgyfel().getNev() + " (szig: " + zalogjegy.getUgyfel().getSzig() + " )");
        model.addAttribute("beadas", zalogjegy.getBeadas());
        model.addAttribute("lejarat", zalogjegyService.futamidoLejarta(zalogjegy.getBeadas()));
        model.addAttribute("leiras", zalogjegy.getLeiras());
        model.addAttribute("karat", zalogjegy.getKarat());
        model.addAttribute("dbSzam", zalogjegy.getDbSzam());
        model.addAttribute("suly", zalogjegy.getSuly());
        model.addAttribute("fizetendo", zalogjegyService.kivaltOsszeg(zalogjegy.getBeadas(), zalogjegy.getOsszeg()));

        return "kivalt";
    }

    @RequestMapping(value = "/kivalt", method = RequestMethod.POST, params = "action=kivalt")
    public String kivaltSubmit(@ModelAttribute Zalogjegy zalogjegy){
        zalogjegyService.kivaltZalogjegy(zalogjegy.getId());
        log.info("Kiváltás " + zalogjegy.getId());

        return "kivalt";
    }

    @RequestMapping("/hosszabbit")
    public String hosszabbit(Model model){
        model.addAttribute("zalogjegy", new Zalogjegy());

        return "hosszabbit";
    }

    @RequestMapping(value = "/hosszabbit", method = RequestMethod.POST, params = "action=ok")
    public String hosszabbitOkSubmit(@ModelAttribute Zalogjegy zalogjegy, Model model){
        log.info("hosszabbit OK gomb");
        zalogjegy = zalogjegyService.getZalogjegy(zalogjegy.getId(), zalogjegy.getOsszeg());

        model.addAttribute("ugyfel", zalogjegy.getUgyfel().getNev() + "\t(szig: " + zalogjegy.getUgyfel().getSzig() + " )");
        model.addAttribute("beadas", zalogjegy.getBeadas());
        model.addAttribute("lejarat", zalogjegyService.futamidoLejarta(zalogjegy.getBeadas()));
        model.addAttribute("leiras", zalogjegy.getLeiras());
        model.addAttribute("karat", zalogjegy.getKarat());
        model.addAttribute("dbSzam", zalogjegy.getDbSzam());
        model.addAttribute("suly", zalogjegy.getSuly());
        model.addAttribute("fizetendo", zalogjegyService.hosszabbitOsszeg(zalogjegy.getBeadas(), zalogjegy.getOsszeg()));

        return "hosszabbit";
    }

    @RequestMapping(value = "/hosszabbit", method = RequestMethod.POST, params = "action=hosszabbit")
    public String hosszabbitSubmit(@ModelAttribute Zalogjegy zalogjegy){
        zalogjegyService.hosszabbitZalogjegy(LocalDate.now(), zalogjegy.getId());

        return "hosszabbit";
}

    @RequestMapping("/kereses")
    public String kereses(Model model) {
        model.addAttribute("ugyfelek", ugyfelService.allUgyfel());

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
    public String ugyfelFelvetSubmit(@ModelAttribute Ugyfel ugyfel, Model model){
        log.info("Új ügyfél felvétele");

        ugyfelService.ujUgyfel(ugyfel.getNev(), ugyfel.getAnyjaNeve(), ugyfel.getSzig(), ugyfel.getCim(), ugyfel.getEmail());

        model.addAttribute("zalogjegy", new Zalogjegy());
        model.addAttribute("ugyfelek", ugyfelService.allUgyfel());
        model.addAttribute("maiNap", LocalDate.now());
        model.addAttribute("lejarat", LocalDate.now().plusDays(90));
        return "felvet";
    }

}
