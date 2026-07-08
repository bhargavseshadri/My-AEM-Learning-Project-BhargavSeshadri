package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.BhargavModelServiceClassTestingService;
import org.osgi.service.component.annotations.Component;

@Component(service = BhargavModelServiceClassTestingService.class)
public class BhargavModelServiceClassTestingServiceImpl implements BhargavModelServiceClassTestingService {

    @Override
    public String BhargavServiceMethod() {
        return "This is a rough service method";
    }

    @Override
    public String sendingToJavaClass() {
        return "Constructor APPROACH :: Service Sending Data -> ";
    }

    @Override
    public String sendingToJavaClassBundleContext() {
        return "Bundle Context APPROACH :: 1. Service Sending Data -> ";
    }
}
