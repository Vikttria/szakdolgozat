package hu.vikttria.zalog_program.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "bejelentkezes";
    }

    @RequestMapping("/kijelentkezes")
    public String kijelentkezes(){
        return "bejelentkezes";
    }

    @RequestMapping("/bejelentkezes")
    public String bejelentkezes(){
        return "bejelentkezes";
    }

}
