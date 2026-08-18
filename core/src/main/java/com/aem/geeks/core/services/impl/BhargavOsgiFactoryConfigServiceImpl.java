package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.config.BhargavOsgiFactoryConfig;
import com.aem.geeks.core.services.BhargavOsgiFactoryConfigService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = BhargavOsgiFactoryConfigService.class)
@Designate(
        ocd = BhargavOsgiFactoryConfig.class,
        factory = true
)
public class BhargavOsgiFactoryConfigServiceImpl implements BhargavOsgiFactoryConfigService {

    private String country;
    private String apiUrl;
    private String apiKey;
    private boolean enabled;

    @Activate
    protected void activate(BhargavOsgiFactoryConfig config) {

        this.country = config.country();
        this.apiUrl = config.apiUrl();
        this.apiKey = config.apiKey();
        this.enabled = config.enabled();
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}