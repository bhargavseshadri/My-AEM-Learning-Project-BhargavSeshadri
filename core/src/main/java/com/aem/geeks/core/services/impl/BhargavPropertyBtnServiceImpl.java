package com.aem.geeks.core.services.impl;


import com.aem.geeks.core.services.BhargavPropertyBtnService;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;


/*BhargavSeshadri :
 * Step : 4 : Last Step - Node prop adding logic
 * For Step: 3 - com/aem/geeks/core/servlets/BhargavPropBtnPathServlet.java
 * */

@Component(service = BhargavPropertyBtnService.class, immediate = true)
public class BhargavPropertyBtnServiceImpl implements BhargavPropertyBtnService {

    @Override
    public boolean addProperty(Resource resource) throws RepositoryException {

        Node node = resource.adaptTo(Node.class);
        if (node != null) {
            node.setProperty("bhargavProperty", "This is a new property added by BhargavPropertyBtnServiceImpl");
            node.getSession().save();
            return true;
        }
        return false;
    }
}
