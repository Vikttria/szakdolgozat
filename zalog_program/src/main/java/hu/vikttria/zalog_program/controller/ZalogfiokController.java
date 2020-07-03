package hu.vikttria.zalog_program.controller;

import hu.vikttria.zalog_program.service.*;
import hu.vikttria.zalog_program.zaloghaz.Dolgozo;
import hu.vikttria.zalog_program.zaloghaz.User;
import hu.vikttria.zalog_program.zaloghaz.Zalogfiok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ZalogfiokController {

    @Autowired
    private ZalogfiokService fiokService;
    @Autowired
    private DolgozoService dolgozoService;
    @Autowired
    private BeosztasService beosztasService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RoleService roleService;


    //Zálogfiókok lekérdezése, felvétele, törlése

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


    //Dolgozók lekérdezése, felvétele, törlése

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

}
