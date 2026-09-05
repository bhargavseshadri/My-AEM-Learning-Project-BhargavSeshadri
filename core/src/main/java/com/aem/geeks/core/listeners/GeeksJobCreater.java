package com.aem.geeks.core.listeners;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.sling.event.jobs.JobManager;


//BhargavSeshadri : Example for "JOB MANAGER"
// Step : 1 -  creating job manager
//Step: 2 - Job Consumer
@Component(service = {EventHandler.class},
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",  //Type of event
                EventConstants.EVENT_FILTER +"=(path=/content/aemgeeks/us/en/hero/*)"  //Under which path this event handler should look
        })
public class GeeksJobCreater implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GeeksJobCreater.class);

    @Reference
    JobManager jobManager;      //Getting the job manager service


    //Mandatory method
    public void handleEvent(final Event event) {
        try {
                Map<String, Object> jobProperties = new HashMap<String, Object>();
                jobProperties.put("event", event.getTopic());
                jobProperties.put("path", event.getProperty(SlingConstants.PROPERTY_PATH));
                jobProperties.put("heropage","heroPage");

                // we have created a map with properties and given it to the jobManager to create a job
                // here "geeks/job" is the cutom topic we have created and using that only we can find the job in "job consumer"
                Job job=jobManager.addJob("geeks/job",jobProperties);


        }catch (Exception e){
            LOG.error("\n Exception is : {} " , e.getMessage());
        }
    }
}

