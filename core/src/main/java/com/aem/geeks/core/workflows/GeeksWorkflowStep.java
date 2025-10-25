package com.aem.geeks.core.workflows;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.Iterator;
import java.util.Set;




@Component(
        service = WorkflowProcess.class,
        property = {
                "process.label" + " = Bhargav Workflow Process STEP",
                Constants.SERVICE_VENDOR + "=BhargavSeshadri",
                Constants.SERVICE_DESCRIPTION + " = Custom Bhargav workflow step."
        }
)
public class GeeksWorkflowStep implements WorkflowProcess {  //To write this we have to impliment an interface called "WorkflowProcess"
    private static final Logger LOG = LoggerFactory.getLogger(GeeksWorkflowStep.class);

    //execute is a Manditory method -
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {  //Manditory method

        /* WorkflowSession: Using this we can get the backend objects, using this only we get the ResourceResolver, Session using this WorkflowSession.
        *  MetaDataMap : If we pass any data to the dialog, that data we can get using this.(in case of custom WF process we get the data we have
                         given in "ARGUMENTS" process step dialog box)(in case of normal process step we can get the data we have given in dialog box of that step).
        *  WorkItem : we might have the steps before and after our process step, and there will be an information and payload flowing from starting of our WF model till end
                      . that payload and the other information should be available in our Custom WF Process and that all information is available in "WorkItem".
                      apart from the available information we can also pass the other information as we want.
        * */


        try {
            LOG.info("\n ====================================Custom Workflow Step========================================");
            WorkflowData workflowData = workItem.getWorkflowData();
            if (workflowData.getPayloadType().equals("JCR_PATH")) {
                Session session = workflowSession.adaptTo(Session.class); //getting the session using "WorkflowSession"

                //here we are getting the payload and then we are going to the /jcr:content node of that.
                String path = workflowData.getPayload().toString() + "/jcr:content";

                //converting that string path in to a Node and getting hold of that node.
                Node node = (Node) session.getItem(path);

                String brand = processArguments.get("BRAND","");
                boolean multinational =processArguments.get("MULTINATIONAL",false);
                LOG.info("\n BRAND : {} , MULTINATIONAL : {} ",brand,multinational);
                String[] countries = processArguments.get("COUNTRIES",new String[]{});
                for (String country : countries) {
                    LOG.info("\n Countries {} ",country);
                }
                session.save();
            }
        }catch (Exception e){
                LOG.info("\n ERROR {} ",e.getMessage());
        }
    }
}



