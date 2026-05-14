package com.aem.geeks.core.msm;

import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.msm.api.LiveAction;
import com.day.cq.wcm.msm.api.LiveRelationship;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;




/*BhargavSeshadri : Step : 2 - Create a Live Action Java class
 *
 * Step : 1 - com/aem/geeks/core/msm/BhargavCustomLiveActionFactory.java
 * Step : 3 - Create Custom Rollout configuration and live action in it - apps/msm/wcm/rolloutconfigs/bhargav-custom-rollout/.content.xml */

public class BhargavCustomLiveAction implements LiveAction {

    private static final Logger LOG = LoggerFactory.getLogger(BhargavCustomLiveAction.class);

    // This method simply Returns action name. just used for debugging.
    @Override
    public String getName() {  //Manditory Method
        return BhargavCustomLiveActionFactory.LIVE_ACTION_NAME;
    }

    /*This is main method
     * Here we will get the reqd objs from aem to add our custom logic as we want.
     * */
    @Override   //Manditory Method
    public void execute(Resource source, Resource target, LiveRelationship relation, boolean autoSave, boolean isResetRollout) throws WCMException {

        try {
            LOG.info("BhargavCustomLiveAction - source: {}", source.getPath());
            LOG.info("BhargavCustomLiveAction - target: {}", target.getPath());
            LOG.info("BhargavCustomLiveAction - autoSave: {}", autoSave);
            LOG.info("BhargavCustomLiveAction - isResetRollout: {}", isResetRollout);
            LOG.info("BhargavCustomLiveAction - LiveRelationship: {} : {}", relation.getRolloutConfigs(), relation.getTargetPath());


            ResourceResolver resourceResolver = source.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);
            String userName = resourceResolver.getUserID();

            // Add property to Blueprint (source)
            Node sourceNode = source.adaptTo(Node.class);
            if (sourceNode != null) {
                sourceNode.setProperty("Bhargav - CustomRolloutExecuted", userName);
            }

            // Add property to Live Copy (target)
            Node targetNode = target.adaptTo(Node.class);
            if (targetNode != null) {
                targetNode.setProperty("Bhargav - CustomRolloutExecuted", userName);
            }

            if (autoSave && session != null) {
                session.save();
            }

        } catch (RepositoryException e) {
            throw new WCMException("Failed to set CustomRolloutExecuted property", e);
        }
    }


    // Deprecated methods - All those below methods are not at all needed but still overrided because the interface still have them.
    @Override
    public void execute(
            ResourceResolver resourceResolver,
            LiveRelationship liveRelationship,
            com.day.cq.wcm.msm.api.ActionConfig actionConfig,
            boolean autoSave) {
    }

    @Override
    public void execute(
            ResourceResolver resourceResolver,
            LiveRelationship liveRelationship,
            com.day.cq.wcm.msm.api.ActionConfig actionConfig,
            boolean autoSave,
            boolean isResetRollout) {
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getRank() {
        return 0;
    }

    @Override
    public String[] getPropertiesNames() {
        return new String[0];
    }

    @Override
    public String getParameterName() {
        return null;
    }

    @Override
    public void write(org.apache.sling.commons.json.io.JSONWriter jsonWriter) {
    }
}