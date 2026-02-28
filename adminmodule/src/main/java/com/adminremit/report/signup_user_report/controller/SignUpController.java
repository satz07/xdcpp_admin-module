package com.adminremit.report.signup_user_report.controller;

import com.adminremit.report.signup_user_report.model.UsersVO;
import com.adminremit.report.signup_user_report.service.SignupService;
import com.adminremit.user.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SignUpController {
    @Autowired
    private SignupService signupService;

    @RequestMapping(value = "/reports/signup-report", method = RequestMethod.GET)
    public ModelAndView getSignup(ModelAndView modelAndView) throws ParseException {

        List<Users> paginatedSignup = signupService.findAll();

        List<UsersVO> usersVOs = new ArrayList<UsersVO>();

        UsersVO vo = null;

        for (Users pd : paginatedSignup) {

            vo = new UsersVO(pd);
            usersVOs.add(vo);

        }
        modelAndView.addObject("Signups", usersVOs);
        modelAndView.setViewName("report/signup-report");
        return modelAndView;
    }

    @RequestMapping(value = "/reports/signup-report/{order}", method = RequestMethod.GET)
    public ModelAndView getSignupBySort(ModelAndView modelAndView, @PathVariable("order") String order) throws ParseException {

        List<Users> paginatedSignup = signupService.findAllBySort(order);

        List<UsersVO> usersVOs = new ArrayList<UsersVO>();
        UsersVO vo = null;

        for (Users pd : paginatedSignup) {

            vo = new UsersVO(pd);
            usersVOs.add(vo);

        }
        modelAndView.addObject("Signups", usersVOs);
        modelAndView.setViewName("report/signup-report");
        return modelAndView;
    }
}
