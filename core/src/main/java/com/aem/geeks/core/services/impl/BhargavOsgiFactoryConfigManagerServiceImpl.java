package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.BhargavOsgiFactoryConfigManagerService;
import com.aem.geeks.core.services.BhargavOsgiFactoryConfigService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(service = BhargavOsgiFactoryConfigManagerService.class)
public class BhargavOsgiFactoryConfigManagerServiceImpl implements BhargavOsgiFactoryConfigManagerService {

    @Reference
    private List<BhargavOsgiFactoryConfigService> configServices;

    @Override
    public BhargavOsgiFactoryConfigService getConfig(String country) {

        for (BhargavOsgiFactoryConfigService service : configServices) {
            if (country.equalsIgnoreCase(service.getCountry())) {
                return service;
            }
        }

        return null;
    }
}
