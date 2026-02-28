package com.adminremit.masters.service;
import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Product;
import com.adminremit.masters.models.LocationMaster;
import com.adminremit.masters.repository.LocationMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationMasterServiceImpl implements LocationMasterService{

    @Autowired
    private LocationMasterRepository locationMasterRepository;

    public LocationMaster save(LocationMaster locationMaster) {
        if(locationMaster.getId() == null) {
            locationMaster.setPublish(true);
            locationMaster = locationMasterRepository.save(locationMaster);
            return locationMaster;
        } else {
            Optional<LocationMaster> locationMaster1 = locationMasterRepository.findById(locationMaster.getId());
            if(locationMaster1.isPresent()) {
                LocationMaster updateLocation = locationMaster1.get();
                updateLocation.setPublish(locationMaster.getPublish());
                updateLocation.setLocationName(locationMaster.getLocationName());
                return locationMasterRepository.save(updateLocation);
            } {
                locationMaster =locationMasterRepository.save(locationMaster);
                return locationMaster;
            }
        }
    }

    public List<LocationMaster> listOfLocation() {
        List<LocationMaster> locationMasters = locationMasterRepository.findAllByIsDeletedOrderByLocationNameAsc(false);
        return locationMasters;
    }

    public LocationMaster getLocationById(Long id) throws RecordNotFoundException {
        Optional<LocationMaster> product = locationMasterRepository.findById(id);
        LocationMaster locationMaster = null;
        if(product.isPresent()) {
            locationMaster = product.get();
        }
        return locationMaster;
    }

    public void deleteLocation(Long id) throws RecordNotFoundException {
        Optional<LocationMaster> locationMaster = locationMasterRepository.findById(id);
        if(locationMaster.isPresent()) {
            LocationMaster locationMaster1 = locationMaster.get();
            locationMaster1.setIsDeleted(true);
            locationMasterRepository.save(locationMaster1);
        }
    }

    @Override
    public List<LocationMaster> listOfAllLocation() {
        List<LocationMaster> locationMasters = locationMasterRepository.findAllByIsDeletedOrderByLocationNameAsc(false);
        return locationMasters;
    }

    @Override
    public LocationMaster getByLocationName(String locationName) {
        LocationMaster locationMaster = null;
        try {
            locationMaster = locationMasterRepository.findFirstByLocationName(locationName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationMaster;
    }
}
