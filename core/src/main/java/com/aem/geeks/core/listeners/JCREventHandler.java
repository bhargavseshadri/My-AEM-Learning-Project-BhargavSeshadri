package com.aem.geeks.core.listeners;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

/*BhargavSeshadri : Example for "JCR Based Event Handler"*/
@Component(immediate = true,service= EventListener.class)
public class JCREventHandler implements EventListener{

    private static final Logger log = LoggerFactory.getLogger(JCREventHandler.class);
    private Session session;

    @Reference
    SlingRepository slingRepository;  //Using this we can get the session.
    

    @Activate
    public void activate() throws Exception {
        try {


            //EVENT LISTENING PART
            String[] nodetypes={"cq:PageContent"};  //this we can give in nodeTypes in place of null for filtering
            session = slingRepository.loginService("seshadribhargavlatestserviceuser",null);  //getting the session using Service User.
            session.getWorkspace().getObservationManager().addEventListener(   //getting "addEventListener" object
                    this,                                               // For current event handler in this java class.
                    Event.NODE_ADDED | Event.PROPERTY_ADDED,         //telling what kind of event this event handler should listen. can take one or more event types.
                    "/content/aemgeeks/us/en/eventhandlingRLTD",          //path
                    true,                                        //is Deep?
                    null,                                    //UUIDs filter
                    null,                                   //nodetypes filter
                    false);

        } catch (RepositoryException e){
            log.info(" \n Error while adding Event Listener : {} ",e.getMessage());
        }
    }



    //EVENT HANDLER PART
    //it is a mandatory method
    public void onEvent(EventIterator eventIterator) {
        try {
            while (eventIterator.hasNext()){
                log.info("\n Path : {} ",eventIterator.nextEvent().getPath());
            }
        } catch(Exception e){
            //log.error("\n Error while processing events : {} ",e.getMessage());
        }
    }

}
