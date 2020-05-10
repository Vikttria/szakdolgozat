package hu.vikttria.zalog_program.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ErrorPageController implements ErrorController {

    private ErrorAttributes errorAttributes;
    @Autowired
    public void setErrorAttributes(ErrorAttributes errorAttributes){
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String error(Model model, HttpServletRequest request){
        ServletWebRequest rA = new ServletWebRequest(request);
        Map<String, Object> error = this.errorAttributes.getErrorAttributes(rA, true);

        model.addAttribute("error", error.get("error"));
        model.addAttribute("status", error.get("status"));

        if (500 == (int)error.get("status")){
            model.addAttribute("message", "Valami hiba van az adatbevitelben");
        }else if(404 == (int)error.get("status")){
            model.addAttribute("message", "Nincs ilyen oldal");
        }else{
            model.addAttribute("message", error.get("message"));
        }

        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
