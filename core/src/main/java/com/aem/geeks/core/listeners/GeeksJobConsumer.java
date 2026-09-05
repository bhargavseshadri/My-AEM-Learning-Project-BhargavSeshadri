package com.aem.geeks.core.listeners;

import com.aem.geeks.core.utils.ResolverUtil;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




//BhargavSeshadri : Example for "JOB CONSUMER"
// Step : 2
// Step : 1 - com/aem/geeks/core/listeners/GeeksJobCreater.java
@Component(service = JobConsumer.class,
            immediate = true,
            property = {
                JobConsumer.PROPERTY_TOPICS + "=geeks/job"  // using this we are linking JOB MANAGER & JOB CONSUMER
        })
public class GeeksJobConsumer implements JobConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(GeeksJobConsumer.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;


    //MANDATORY METHOD
    //HANDLER
    @Override
    public JobResult process(Job job) { // this JOB object will have whatever we have given in the JobManager "handleEvent(final Event event)" method
        try {
            ResourceResolver resourceResolver= ResolverUtil.newResolver(resourceResolverFactory);
            String path = (String) job.getProperty("path");             // This path is the one we given in the Job Manager, here we are getting it
            String event= (String) job.getProperty("event");            // This event is the one we given in the Job Manager, here we are getting it
            String heropage=(String) job.getProperty("heropage");
            LOG.info("\n Job executing for  : {} ",resourceResolver.getResource(heropage).getName());
            return JobResult.OK;
        } catch (Exception e) {
            LOG.info("\n Error in Job Consumer : {}  ", e.getMessage());
            return JobResult.FAILED;
        }
    }
}
