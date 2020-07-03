package hu.vikttria.zalog_program.controller;

import hu.vikttria.zalog_program.service.*;
import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import hu.vikttria.zalog_program.zaloghaz.User;
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
public class UgyfelController {

    @Autowired
    private UgyfelService ugyfelService;
    @Autowired
    private ZalogjegyService zalogjegyService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RoleService roleService;


    //Új ügyfél felvétele

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


    //Ügyfél felülete

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

}
