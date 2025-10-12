package com.aem.geeks.core.schedulers;

import com.aem.geeks.core.config.SchedulerConfiguration;
import org.apache.sling.commons.scheduler.Job;
import org.apache.sling.commons.scheduler.JobContext;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*BhargavSeshadri - STEP:1 - JOB SCHEDULER
* for Step:2 - create a configuration to take the values for this scheduler -  com/aem/geeks/core/config/SchedulerConfiguration.java
* STEP:3 (LAST STEP) it is also here in this file - add all the required scheduler code using osgi config values
* */

@Component(immediate = true, service = Job.class)  //here we are making this class as a OSGI Componet, and maing our service as a job service
@Designate(ocd = SchedulerConfiguration.class)  //here we are designation the configuration file to this service
public class GeeksSchedulerJobs implements Job {   //Implimenting the JOB interface to make this as a job scheduler
    private static final Logger LOG = LoggerFactory.getLogger(GeeksSchedulerJobs.class);

    private int schedulerJobId;

    @Reference
    private Scheduler scheduler;

    @Activate
    protected void activate(SchedulerConfiguration config) {
        addSchedulerJob(config);
        schedulerJobId = config.schedulerName().hashCode();
    }

    @Deactivate
    protected void deactivate(SchedulerConfiguration config) {
        removeSchedulerJob();
    }

    private void removeSchedulerJob() {
        scheduler.unschedule(String.valueOf(schedulerJobId));
    }

    private void addSchedulerJob(SchedulerConfiguration config) {  //MAIN LOGIC - Here in this method we are defining three different jobs for three different countries with three different corns

        ScheduleOptions in = scheduler.EXPR("0 03 17 1/1 * ? *");
        Map<String, Serializable> inMap=new HashMap<>();  //Here we are creating a map
        inMap.put("country","IN");                         //we are putting the data in to the map
        inMap.put("url","www.in.com");
        in.config(inMap);                       //we are getting ".config(Map)" method from ScheduleOptions

        scheduler.schedule(this,in);
        ScheduleOptions de = scheduler.EXPR("0 04 17 1/1 * ? *");
        Map<String, Serializable> deMap=new HashMap<>();
        deMap.put("country","DE");
        deMap.put("url","www.de.com");
        de.config(deMap);
        scheduler.schedule(this,de);

        ScheduleOptions us = scheduler.EXPR("0 05 17 1/1 * ? *");
        Map<String, Serializable> usMap=new HashMap<>();
        usMap.put("country","US");
        usMap.put("url","www.us.com");
        us.config(usMap);
        scheduler.schedule(this,us);
    }

    //MANDITORY METHOD - this will get executed based on the provide cron expression
    @Override
    public void execute(JobContext jobContext) {         //JOBCONTEXT : using this we can add & get the job specific custom data and print it.
           LOG.info("\n =======> COUNTRY {} : URL {} ",jobContext.getConfiguration().get("country"),  //using this ".getConfiguration()" we can get the data from the map we have passed to the .config(Map) above.
                   jobContext.getConfiguration().get("url"));   //Whatecer JOB is executing then this ".getConfiguration()" have that job specific values.
    }
}
