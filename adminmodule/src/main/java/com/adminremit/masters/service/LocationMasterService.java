package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.auth.models.Product;
import com.adminremit.masters.models.LocationMaster;

import java.util.List;

public interface LocationMasterService {
    public LocationMaster save(LocationMaster locationMaster);
    public List<LocationMaster> listOfLocation();
    public LocationMaster getLocationById(Long id) throws RecordNotFoundException;
    public void deleteLocation(Long id) throws RecordNotFoundException;
    public List<LocationMaster> listOfAllLocation();
    public LocationMaster getByLocationName(String locationName);
}
