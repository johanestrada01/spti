package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.exceptions.UserException;
import co.edu.eci.cvds.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    public static boolean login = false;

    @Autowired
    UserService userService;


    @Autowired
    public LoginController() {
        this.login = false;
    }

    @GetMapping("/test")
    public String loginTest() {
        return "login_oficial";
    }

    @PostMapping("/correct")
    public String loginCorrect(@RequestParam("usuario") String email, @RequestParam("clave") String password){
        login(email, password);
        if(login) {
            return "redirect:/user/getQuotation";
        }
        return "login_oficial";
    }

    @GetMapping("/correct")
    public String loginNoCorrect(){
        login = false;
        return "login_oficial";
    }


    public void login(String email, String password){
        try {
            login = userService.login(email, password);
        } catch (UserException e) {
            login = false;
        }
    }

    public static boolean isLogin(){
        return login;
    }

}
