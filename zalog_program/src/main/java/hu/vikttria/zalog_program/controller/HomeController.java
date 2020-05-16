package hu.vikttria.zalog_program.controller;

import hu.vikttria.zalog_program.service.*;
import hu.vikttria.zalog_program.zaloghaz.*;
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
public class HomeController {

    @Autowired
    private ZalogjegyService zalogjegyService;
    @Autowired
    private UgyfelService ugyfelService;
    @Autowired
    private ZalogfiokService fiokService;
    @Autowired
    private DolgozoService dolgozoService;
    @Autowired
    private BeosztasService beosztasService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RoleService roleService;


    @RequestMapping("/")
    public String home(){
        return "bejelentkezes";
    }

    @RequestMapping("/kijelentkezes")
    public String kijelentkezes(){
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

        String jelszo = ugyfelService.jelszo();

        ugyfelService.ujUgyfel(ugyfel.getNev(), ugyfel.getAnyjaNeve(), ugyfel.getSzig(), ugyfel.getCim(), ugyfel.getEmail());

        emailService.uzenetKuldesUgyfel(ugyfel.getEmail(), ugyfel.getNev(), jelszo);

        User user = new User(ugyfel.getEmail(), jelszo, ugyfelService.ugyfelEmail(ugyfel.getEmail()), roleService.roleSearch(3));
        userServiceImpl.save(user);

        model.addAttribute("zalogjegy", new Zalogjegy());
        model.addAttribute("ugyfelek", ugyfelService.allUgyfel());
        model.addAttribute("maiNap", LocalDate.now());
        model.addAttribute("lejarat", LocalDate.now().plusDays(90));

        return "felvet";
    }

    @RequestMapping(value = "/ugyfel")
    public String ugyfel(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        model.addAttribute("ugyfel", new Ugyfel());
        model.addAttribute("zalogok", zalogjegyService.ugyfelId(ugyfelService.ugyfelEmail(name).getId()));
        model.addAttribute("kivaltDatum", LocalDate.now());

        return "ugyfel";
    }

    @RequestMapping(value = "/ugyfelOk", method = RequestMethod.POST)
    public String ugyfelSubmit(@ModelAttribute Ugyfel ugyfel,
                         @RequestParam(value = "kivaltDatum") String kivaltDatum,
                         @RequestParam(value = "zalogId") String zalogId,
                         Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Zalogjegy zalogjegy = zalogjegyService.zalogjegyId(Long.parseLong(zalogId));

        model.addAttribute("ugyfel", new Ugyfel());
        model.addAttribute("zalogjegy", new Zalogjegy());
        model.addAttribute("zalogok", zalogjegyService.ugyfelId(ugyfelService.ugyfelEmail(name).getId()));
        model.addAttribute("kivaltDatum", kivaltDatum);

        model.addAttribute("cim", zalogjegy.getZalogfiok().getCim());
        model.addAttribute("tel", zalogjegy.getZalogfiok().getTelefon());
        model.addAttribute("beadOsszeg", zalogjegy.getOsszeg());
        model.addAttribute("beadDatum", zalogjegy.getBeadas());
        model.addAttribute("lejarDatum", zalogjegyService.futamidoLejarta(zalogjegy.getBeadas()));
        model.addAttribute("leiras", zalogjegy.getLeiras());
        model.addAttribute("kamat", zalogjegyService.lekerKamatOsszeg(zalogjegy.getBeadas(), LocalDate.parse(kivaltDatum), zalogjegy.getOsszeg()));
        model.addAttribute("kivaltas", zalogjegyService.lekerKivaltOsszeg(zalogjegy.getBeadas(), LocalDate.parse(kivaltDatum), zalogjegy.getOsszeg()));

        return "ugyfel";
    }

    @RequestMapping("/zalogfiok")
    public String zalogfiok(Model model){

        model.addAttribute("zalogfiok", new Zalogfiok());
        model.addAttribute("zalogfiokok", fiokService.allFiok());

        return "zalogfiok";
    }

    @RequestMapping(value = "/zalogfiokUj", method = RequestMethod.POST)
    public String zalogfiokUjSubmit(@ModelAttribute Zalogfiok zalogfiok,
                                    @RequestParam(value = "jelszo") String jelszo,
                                    Model model){

        fiokService.ujFiok(zalogfiok.getCim(), zalogfiok.getTelefon());

        User user = new User(zalogfiok.getCim(), jelszo, roleService.roleSearch(2));
        userServiceImpl.save(user);

        model.addAttribute("zalogfiok", new Zalogfiok());
        model.addAttribute("zalogfiokok", fiokService.allFiok());

        return "zalogfiok";
    }

    @RequestMapping(value = "/fiokTorol", method = RequestMethod.POST)
    public String fiokTorolSubmit(@ModelAttribute Zalogfiok fiok, Model model){

        fiokService.fiokTorol(fiok.getId());

        model.addAttribute("zalogfiok", new Zalogfiok());
        model.addAttribute("zalogfiokok", fiokService.allFiok());

        return "zalogfiok";
    }

    @RequestMapping("/dolgozo")
    public String dolgozo(Model model){

        model.addAttribute("dolgozo", new Dolgozo());
        model.addAttribute("dolgozok", dolgozoService.allDolgozo());
        model.addAttribute("zalogfiokok", fiokService.allFiok());
        model.addAttribute("beosztasok", beosztasService.allBeosztas());

        return "dolgozo";
    }

    @RequestMapping(value = "/dolgozo", method = RequestMethod.POST)
    public String dolgozoUjSubmit(@ModelAttribute Dolgozo dolgozo, Model model){

        dolgozoService.ujDolgozo(dolgozo.getNev(), dolgozo.getTelefon(), dolgozo.getEmail(), dolgozo.getZalogfiok(), dolgozo.getBeosztas());

        model.addAttribute("dolgozo", new Dolgozo());
        model.addAttribute("dolgozok", dolgozoService.allDolgozo());
        model.addAttribute("zalogfiokok", fiokService.allFiok());
        model.addAttribute("beosztasok", beosztasService.allBeosztas());

        return "dolgozo";
    }

    @RequestMapping(value = "/dolgozoTorol", method = RequestMethod.POST)
    public String dolgozoTorolSubmit(@ModelAttribute Dolgozo dolgozo, Model model){

        dolgozoService.dolgozoTorol(dolgozo.getId());

        model.addAttribute("dolgozo", new Dolgozo());
        model.addAttribute("dolgozok", dolgozoService.allDolgozo());
        model.addAttribute("zalogfiokok", fiokService.allFiok());
        model.addAttribute("beosztasok", beosztasService.allBeosztas());

        return "dolgozo";
    }

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


    private void kezeles(@ModelAttribute Zalogjegy zalogjegy, Model model) {

        model.addAttribute("ugyfel", zalogjegy.getUgyfel().getNev() + " (szig: " + zalogjegy.getUgyfel().getSzig() + " )");
        model.addAttribute("beadas", zalogjegy.getBeadas());
        model.addAttribute("lejarat", zalogjegyService.futamidoLejarta(zalogjegy.getBeadas()));
        model.addAttribute("leiras", zalogjegy.getLeiras());
        model.addAttribute("karat", zalogjegy.getKarat());
        model.addAttribute("dbSzam", zalogjegy.getDbSzam());
        model.addAttribute("suly", zalogjegy.getSuly());
    }

}
