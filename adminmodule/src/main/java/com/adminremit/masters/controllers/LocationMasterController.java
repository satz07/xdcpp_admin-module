package com.adminremit.masters.controllers;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Product;
import com.adminremit.auth.service.ProductService;
import com.adminremit.masters.models.Currencies;
import com.adminremit.masters.models.LocationMaster;
import com.adminremit.masters.models.PaymentReceiveMode;
import com.adminremit.masters.service.LocationMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class LocationMasterController {

    @Autowired
    private LocationMasterService locationMasterService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/location", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView, LocationMaster locationMaster) {
        modelAndView.addObject("location",locationMaster);
        modelAndView.setViewName("masters/location/create");
        return modelAndView;
    }

    @RequestMapping(value = "/location/create", method = RequestMethod.POST)
    public ModelAndView createLocation(ModelAndView modelAndView, @Valid LocationMaster locationMaster, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("location",locationMaster);
            modelAndView.addObject("errorMessage", "Country is mandatory");
            modelAndView.setViewName("masters/location/create");
            return modelAndView;
        } else {
            LocationMaster locationMaster1 = locationMasterService.getByLocationName(locationMaster.getLocationName());
            if(locationMaster1 != null && locationMaster1.getId() != locationMaster.getId()) {
                modelAndView.addObject("location",locationMaster);
                modelAndView.addObject("errorMessage", "Duplicate value!!!");
                modelAndView.setViewName("masters/location/create");
                return modelAndView;
            }
            if(locationMaster1 != null && locationMaster.getId() == null) {
                modelAndView.addObject("location",locationMaster);
                modelAndView.addObject("errorMessage", "Duplicate value!!!");
                modelAndView.setViewName("masters/location/create");
                return modelAndView;
            }
            locationMasterService.save(locationMaster);
            modelAndView.addObject("confirmationMessage", "Location has been created");
            List<LocationMaster> locationMasterList = locationMasterService.listOfAllLocation();
            modelAndView.addObject("locations",locationMasterList);
            modelAndView.setViewName("masters/location/list");
        }
        return new ModelAndView("redirect:/location/list");
    }

    @RequestMapping(path = {"/location/edit", "/location/edit/{id}"})
    public ModelAndView editLocation(ModelAndView model, @PathVariable("id") Optional<Long> id, BindingResult bindingResult) throws RecordNotFoundException
    {
        if(bindingResult.hasErrors()) {
            model.setViewName("masters/location/create");
        }
        if (id.isPresent()) {
            LocationMaster entity = locationMasterService.getLocationById(id.get());
            model.addObject("location", entity);
            model.setViewName("masters/location/create");
        } else {
            model.addObject("location", new Product());
            model.setViewName("masters/location/create");
        }
        return model;
    }

    @RequestMapping(value = "/location/list", method = RequestMethod.GET)
    public ModelAndView listProducts(ModelAndView modelAndView, BindingResult bindingResult) {
        List<LocationMaster> locationMasters = locationMasterService.listOfAllLocation();
        modelAndView.addObject("locations",locationMasters);
        modelAndView.setViewName("masters/location/list");
        return modelAndView;
    }

    @RequestMapping(value = "/location/delete/{id}",method = RequestMethod.GET)
    public ModelAndView deleteLocation(ModelAndView modelAndView, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException{
        List<Product> productList = productService.listOfAllProductsByLocation(id.get());
        if(productList.size() == 0) {
            locationMasterService.deleteLocation(id.get());
            modelAndView.addObject("confirmationMessage", "Location has been deleted!!");
            List<LocationMaster> locationMasters = locationMasterService.listOfAllLocation();
            modelAndView.addObject("locations",locationMasters);
            modelAndView.setViewName("masters/location/list");
            return modelAndView;
        } else  {
            modelAndView.addObject("errorMessage", "You can't delete this location!!!");
            List<LocationMaster> locationMasters = locationMasterService.listOfAllLocation();
            modelAndView.addObject("locations",locationMasters);
            modelAndView.setViewName("masters/location/list");
            return modelAndView;
        }
    }
    @RequestMapping(value = "/api/location/deletelocationapi/{id}", method = RequestMethod.GET)
	public @ResponseBody  String deleteLocationById(@PathVariable("id") Optional<Long> id)throws RecordNotFoundException {
    	 List<Product> productList = productService.listOfAllProductsByLocation(id.get());
    	 if(productList.size() == 0) {
    		 locationMasterService.deleteLocation(id.get());
			List<LocationMaster> locationMasters = locationMasterService.listOfAllLocation();
			return "Deleted Succefully";
		} else {
			  List<LocationMaster> locationMasters = locationMasterService.listOfAllLocation();
			return "You can't delete this location!!! ";
		}

	}
}
