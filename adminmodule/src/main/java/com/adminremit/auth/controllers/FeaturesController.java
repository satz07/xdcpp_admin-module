package com.adminremit.auth.controllers;

import com.adminremit.auth.models.FeaturesMaster;
import com.adminremit.auth.service.FeaturesMasterService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FeaturesController {

    @Autowired
    private FeaturesMasterService featuresMasterService;

    @ResponseBody
    @RequestMapping(value = "load/features/{id}", method = RequestMethod.GET)
    public String loadFeaturesByFunction(@PathVariable("id") String id) throws Exception {
        Gson gson = new Gson();
        List<FeaturesMaster> featuresMasters = featuresMasterService.listOfFeaturesByFunction(Long.parseLong(id));
        return gson.toJson(featuresMasters);
    }

}
