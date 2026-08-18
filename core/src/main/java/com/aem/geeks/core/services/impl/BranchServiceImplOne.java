package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.MainServiceInterface;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

@Component(service = MainServiceInterface.class)
@ServiceRanking(1000)
public class BranchServiceImplOne implements MainServiceInterface {
    @Override
    public String branchServiceMessage() {
        return "Message from -- BranchServiceImplOne";
    }
}
