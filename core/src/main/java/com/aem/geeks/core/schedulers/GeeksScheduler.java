package com.aem.geeks.core.schedulers;

import com.aem.geeks.core.config.SchedulerConfiguration;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*BhargavSeshadri - STEP:1 - Runnable Scheduler
* Here we create a scheduler
* STEP: 2: com/aem/geeks/core/config/SchedulerConfiguration.java - create a configuration and then come here and connect that config and use the values
* STEP:3 (LAST STEP) it is also here in this file - add all the required scheduler code using osgi config values
* */


@Component(immediate = true, service = Runnable.class)        //here we are creating a osgi component by giving @component and then we give service = Runnable.class
@Designate(ocd = SchedulerConfiguration.class)         //STEP:2 -  we should have a OSGI Configuration for giveing the values to the schedulesr
public class GeeksScheduler implements Runnable {  //Here we are implimenting Runnable interface to make this as Runnable Scheduler
    private static final Logger LOG = LoggerFactory.getLogger(GeeksScheduler.class);

    private int schedulerId;

    @Reference
    private Scheduler scheduler;      //we are getting the scheduler obj to use it

    @Activate
    protected void activate(SchedulerConfiguration config) {  //here in this activate method we are getting the Configuration object to get the values
        schedulerId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    @Deactivate
    protected void deactivate(SchedulerConfiguration config) {   //Deactivate method to stop the scheduler from executing
        removeScheduler();
    }

    protected void removeScheduler() {
        scheduler.unschedule(String.valueOf(schedulerId));
    }

    protected void addScheduler(SchedulerConfiguration config) {   //Main Logic
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.cronExpression());  // here we got the ScheduleOptions using scheduler, and for that ScheduleOptions we are providing our cron expression
        scheduleOptions.name(String.valueOf(schedulerId));  //we should always add an uniquely identifing name
        //scheduleOptions.canRunConcurrently(true);
        scheduler.schedule(this, scheduleOptions);   //here using ".schedule" we are scheduling the scheduler


        //So in some case, we have set the scheduler to exceute at 10 AM in the morning, so after newly/freshly the scheduler gets deployed today at 8pm, the in that case for
        //between 8pm and 10 am the scheduler wont run and the frontend will be empty, to resolve this problem
        ScheduleOptions scheduleOptionsNow = scheduler.NOW(); /*with this the scheduler wii deploy one time immediately after deployed, so as soon as the
                                                                code deployed the run method will execute once.*/
        ScheduleOptions scheduleOptionsTimes = scheduler.NOW(3,5); /*So according to this the scheduler will execute 3-times with 5-second time interval,
                                                                        and after third time then it will execute based on the give corn */
        scheduler.schedule(this, scheduleOptionsNow);

    }


   @Override
    public void run() {         //MANDITORY METHOD - THIS METHOD WILL GETS EXECUTE FOR WHATEVER ScheduleOptions WE HAVE PROVIDED
       LOG.info("\n ====> RUN METHOD in runnable interface gets executed - Bhargav");
    }
}
