package com.adminremit.auth.converter;
import org.springframework.core.convert.converter.Converter;
import com.adminremit.auth.models.FeaturesMaster;
import com.adminremit.auth.service.FeaturesMasterService;
import org.springframework.beans.factory.annotation.Autowired;

public class FeatureMasterConverter implements Converter<String, FeaturesMaster> {

    @Autowired
    private FeaturesMasterService featuresMasterService;

    @Override
    public FeaturesMaster convert(String id) {
        try {
            return featuresMasterService.getById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
