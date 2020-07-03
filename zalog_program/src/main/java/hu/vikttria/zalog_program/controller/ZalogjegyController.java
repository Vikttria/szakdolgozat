package hu.vikttria.zalog_program.controller;

import hu.vikttria.zalog_program.service.UgyfelService;
import hu.vikttria.zalog_program.service.ZalogfiokService;
import hu.vikttria.zalog_program.service.ZalogjegyService;
import hu.vikttria.zalog_program.zaloghaz.Zalogjegy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ZalogjegyController {

    @Autowired
    private ZalogjegyService zalogjegyService;
    @Autowired
    private UgyfelService ugyfelService;
    @Autowired
    private ZalogfiokService fiokService;


    private void kezeles(@ModelAttribute Zalogjegy zalogjegy, Model model) {

        model.addAttribute("ugyfel", zalogjegy.getUgyfel().getNev() + " (szig: " + zalogjegy.getUgyfel().getSzig() + " )");
        model.addAttribute("beadas", zalogjegy.getBeadas());
        model.addAttribute("lejarat", zalogjegyService.futamidoLejarta(zalogjegy.getBeadas()));
        model.addAttribute("leiras", zalogjegy.getLeiras());
        model.addAttribute("karat", zalogjegy.getKarat());
        model.addAttribute("dbSzam", zalogjegy.getDbSzam());
        model.addAttribute("suly", zalogjegy.getSuly());
    }


    //Zálogjegy felvétel

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        zalogjegyService.ujZalog(zalogjegy.getLeiras(),
                zalogjegy.getKarat(),
                zalogjegy.getSuly(),
                zalogjegy.getDbSzam(),
                zalogjegy.getOsszeg(),
                LocalDate.now(),
                zalogjegy.getUgyfel(),
                fiokService.zalogjegyCim(name));

        model.addAttribute("zalogjegy", new Zalogjegy());
        model.addAttribute("ugyfelek", ugyfelService.allUgyfel());
        model.addAttribute("maiNap", LocalDate.now());
        model.addAttribute("lejarat", zalogjegyService.futamidoLejarta(LocalDate.now()));

        return "felvet";
    }


    //Zálogjegy kiváltása

    @RequestMapping("/kivalt")
    public String kivalt(Model model){

        model.addAttribute("zalogjegy", new Zalogjegy());

        return "kivalt";
    }

    @RequestMapping(value = "/kivalt", method = RequestMethod.POST, params = "action=ok")
    public String kivaltOkSubmit(@ModelAttribute Zalogjegy zalogjegy, Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        zalogjegy = zalogjegyService.getZalogjegyKivalt(zalogjegy.getId(), zalogjegy.getOsszeg(), fiokService.zalogjegyCim(name));

        kezeles(zalogjegy, model);
        model.addAttribute("fizetendo", zalogjegyService.kivaltOsszeg(zalogjegy.getBeadas(), zalogjegy.getOsszeg()));

        return "kivalt";
    }

    @RequestMapping(value = "/kivalt", method = RequestMethod.POST, params = "action=kivalt")
    public String kivaltSubmit(@ModelAttribute Zalogjegy zalogjegy){

        zalogjegyService.kivaltZalogjegy(zalogjegy.getId());

        return "kivalt";
    }


    //Zálogjegy hosszabbítása

    @RequestMapping("/hosszabbit")
    public String hosszabbit(Model model){

        model.addAttribute("zalogjegy", new Zalogjegy());

        return "hosszabbit";
    }

    @RequestMapping(value = "/hosszabbit", method = RequestMethod.POST, params = "action=ok")
    public String hosszabbitOkSubmit(@ModelAttribute Zalogjegy zalogjegy, Model model){

        zalogjegy = zalogjegyService.getZalogjegyHosszabbit(zalogjegy.getId(), zalogjegy.getOsszeg());

        kezeles(zalogjegy, model);
        model.addAttribute("fizetendo", zalogjegyService.hosszabbitOsszeg(zalogjegy.getBeadas(), zalogjegy.getOsszeg()));

        return "hosszabbit";
    }

    @RequestMapping(value = "/hosszabbit", method = RequestMethod.POST, params = "action=hosszabbit")
    public String hosszabbitSubmit(@ModelAttribute Zalogjegy zalogjegy){

        zalogjegyService.hosszabbitZalogjegy(LocalDate.now(), zalogjegy.getId());

        return "hosszabbit";
    }


    //Zálogjegy lekérdezése

    @RequestMapping("/lekerdez")
    public String lekerdez(Model model){

        model.addAttribute("zalogjegy", new Zalogjegy());
        model.addAttribute("kivaltDatum", LocalDate.now());

        return "lekerdez";
    }

    @RequestMapping(value = "/lekerdez", method = RequestMethod.POST)
    public String okSubmit(@ModelAttribute Zalogjegy zalogjegy,
                           @RequestParam(value = "kivaltDatum") String kivaltDatum,
                           Model model){

        zalogjegy = zalogjegyService.getZalogjegyHosszabbit(zalogjegy.getId(), zalogjegy.getOsszeg());

        kezeles(zalogjegy, model);

        model.addAttribute("hosszabbitas", zalogjegyService.lekerKamatOsszeg(zalogjegy.getBeadas(), LocalDate.parse(kivaltDatum), zalogjegy.getOsszeg()));
        model.addAttribute("kivaltas", zalogjegyService.lekerKivaltOsszeg(zalogjegy.getBeadas(), LocalDate.parse(kivaltDatum), zalogjegy.getOsszeg()));
        model.addAttribute("kivaltDatum", kivaltDatum);

        return "lekerdez";
    }


    //Zálogjegy bevonása kényszerértékesítésre

    @RequestMapping("/bevon")
    public String bevon(Model model){

        model.addAttribute("zalogjegy", new Zalogjegy());
        model.addAttribute("datum", LocalDate.now().minusDays(90));

        return "bevon";
    }

    @RequestMapping(value = "/bevon", method = RequestMethod.POST, params = "action=listaz")
    public String listazSubmit(@ModelAttribute Zalogjegy zalogjegy,
                               @RequestParam(value = "datum") String datum,
                               Model model){

        model.addAttribute("zalogjegyek", zalogjegyService.datumElotti(LocalDate.parse(datum)));
        model.addAttribute("datum", datum);

        return "bevon";
    }

    @RequestMapping(value = "/bevon", method = RequestMethod.POST, params = "action=bevonas")
    public String bevonasSubmit(@ModelAttribute Zalogjegy zalogjegy,
                                @RequestParam(value = "datum") String datum,
                                Model model){

        zalogjegyService.bevonasDatumElotti(LocalDate.parse(datum));

        model.addAttribute("datum", LocalDate.now().minusDays(90));

        return "bevon";
    }

}
