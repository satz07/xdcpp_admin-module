package com.adminremit.home.controllers;

import com.adminremit.auth.models.User;
import com.adminremit.auth.service.AuthService;
import com.adminremit.common.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @RequestMapping("/")
    @Secured("ROLE_MASTER")
    public String index(HttpServletRequest httpServletRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().stream().filter(o -> o.getAuthority().equals("ROLE_SUPERADMIN")).findFirst().isPresent()) {
            return "redirect:/product/list";
        } else {
            return "redirect:/user/list";
        }
    }

    @RequestMapping("/home")
    @RolesAllowed({"MASTERS"})
    public ModelAndView home(HttpServletRequest httpServletRequest) {
        User user = authService.getUser();
        String message = "Welcome to Remit "+user.getFirstName();
        return new ModelAndView("home/home", "message", message);
    }

    @RequestMapping("/sendEmail")
    public void sendEmail(HttpServletRequest httpServletRequest) {
        emailService.sendMail("sathishauit@gmail.com","Test Email","Message content");
    }
    
    @RequestMapping("/product/complaince/complaincemaker")
    public ModelAndView Compliance(HttpServletRequest httpServletRequest) {
        User user = authService.getUser();
        //String message = "Welcome to Remit "+user.getFirstName();
        return new ModelAndView("complaince/complaincemaker");
    }

}
