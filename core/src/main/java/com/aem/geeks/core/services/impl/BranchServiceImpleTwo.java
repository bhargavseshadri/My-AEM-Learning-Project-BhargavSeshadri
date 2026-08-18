package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.MainServiceInterface;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

@Component(service = MainServiceInterface.class)
@ServiceRanking(1001)
public class BranchServiceImpleTwo implements MainServiceInterface {

    @Override
    public String branchServiceMessage() {
        return "Message from -- BranchServiceImpleTwo";
    }

}
