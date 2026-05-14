package com.aem.geeks.core.msm;

import com.day.cq.wcm.msm.api.LiveAction;
import com.day.cq.wcm.msm.api.LiveActionFactory;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

/*BhargavSeshadri : Step : 1 - Create a Live Action Factory Java class
*
* Step : 2 - com/aem/geeks/core/msm/BhargavCustomLiveActionFactory.java*/

/*
* FLOW to get here
* Blueprint page change
      ↓
  Rollout congig triggered
      ↓
  AEM reads rollout config
      ↓
  Reads cq:action value in it - and that value is cq:action = bhargavCustomLiveAction
      ↓
  AEM looks for the LiveActionFactory which creates action with name "bhargavCustomLiveAction"
      ↓
  AEM finds our BhargavCustomLiveActionFactory and calls its createAction method
  *
  *
  *
  *
  *-- Important--
  * In our rollout config, we have to give the same name in 4 places :
* 1) LiveActionFactory property -> LiveActionFactory.LIVE_ACTION_NAME + "=bhargavCustomLiveAction"
* 2) liveaction node name in out custom rollout -> /apps/msm/wcm/rolloutconfigs/bhargav-custom-rollout/jcr:content/bhargavCustomLiveAction
* 3) cq:action property value in out liveAction node. cq:action = bhargavCustomLiveAction
* 4) Same name should be returned in createsAction method of LiveActionFactory. return "bhargavCustomLiveAction";
* */


//This class acts like a creator/factory. AEM never directly creates your action class. Instead AEM asks factory: "Give me action object for this rollout action"

@Component(
        service = LiveActionFactory.class,
        property = {
                /*Very Manditory : we have to give this below name while creating live action in our custom rollout configuration, otherwise our
                 aem won't know which rollout to execute. Most importantly we have to give the same name to the liveaction node and inside it also we have to
                 give same name to the property -> cq:action. then only our AEM show this custom rollout config in the dropdown.*/
                LiveActionFactory.LIVE_ACTION_NAME + "=bhargavCustomLiveAction"
        }
)
public class BhargavCustomLiveActionFactory implements LiveActionFactory {


    /* After AEM find this LiveActionFactory, it will call this method to check which live action this factory creates,
       and if the name matches with the "cq:action" value in rollout config, then it will execute the createAction method.*/
    @Override
    public String createsAction() {  //Manditory Method
        return "bhargavCustomLiveAction";
    }


    /*Now after it got the confit=rmation in the above createsAction() method, it will now comes to this method, and the below class object
      have the actual executionable object, now the flow will go to the live action class*/
    @Override
    public LiveAction createAction(Resource resource) {   //Manditory Method
        return new BhargavCustomLiveAction();
    }
}