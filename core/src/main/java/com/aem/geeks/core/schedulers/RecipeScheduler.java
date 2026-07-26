package com.aem.geeks.core.schedulers;

import com.aem.geeks.core.services.RecipeSchedulerService;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/*BhargavSeshadri - S
TEP: 1 - create a content fragment model with these fiedls - recipeId (number), name (single line text), servings (number), difficulty (single line text),
          cuisine (single line text), mealType (mutiline text)
STEP: 2 - Create a folder for our content fragments in assets - /content/dam/bhargav-cf-model/bhargav-cf-recipes-folder
STEP: 3 (CURRENT)- Create a Scheduler - com/aem/geeks/core/schedulers/RecipeScheduler.java

For step:4 - ServiceImpl - com/aem/geeks/core/services/impl/RecipeSchedulerServiceImpl.java*/

@Component(immediate = true, service = Runnable.class)
public class RecipeScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeScheduler.class);
    private static final String JOB_NAME = "RecipeContentFragmentScheduler";
    private static final int MAX_CF_COUNT = 5;
    private int createdCount = 0;

    @Reference
    private Scheduler scheduler;

    @Reference
    private RecipeSchedulerService recipeSchedulerService;

    @Activate
    protected void activate() {
        LOG.debug("RecipeScheduler Started - Activate method executed");

        ScheduleOptions options = scheduler.EXPR("0 0/2 * * * ?"); //Uncomment this line to make the functionality to start and work
        options.name(JOB_NAME);
        options.canRunConcurrently(false);
        scheduler.schedule(this, options);
    }

    @Deactivate
    protected void deactivate() {
        scheduler.unschedule(JOB_NAME);
        LOG.debug("Recipe Scheduler stopped.");
    }

    @Override
    public void run() {
        LOG.debug("RecipeScheduler - Entered run method");
        if (createdCount >= MAX_CF_COUNT) {
            LOG.info("Maximum limit of {} Content Fragments reached. Unscheduling job.", MAX_CF_COUNT);
            scheduler.unschedule(JOB_NAME);
            return;
        }

        try {
            recipeSchedulerService.createNextRecipeContentFragment();
            createdCount++;
            LOG.info("Execution completed successfully. Created Count : {}/{}", createdCount, MAX_CF_COUNT);
        } catch (Exception e) {
            LOG.error("Error while creating Content Fragment.", e);
        }
    }
}