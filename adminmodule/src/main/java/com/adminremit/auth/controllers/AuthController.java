package com.adminremit.auth.controllers;

import com.adminremit.auth.service.AuthService;
import org.mockito.internal.util.collections.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping("/login")
    public String index(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        return "login/index";
    }

    @RequestMapping("/landing")
    public String landing() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOG.info("Authentication Successfull"+authentication.getPrincipal().toString());
        if(authentication.getAuthorities().stream().filter(o -> o.getAuthority().equals("ROLE_SUPERADMIN")).findFirst().isPresent()) {
            return "redirect:/product/list";
        } else {
            return "redirect:/user/list";
        }
    }
}
