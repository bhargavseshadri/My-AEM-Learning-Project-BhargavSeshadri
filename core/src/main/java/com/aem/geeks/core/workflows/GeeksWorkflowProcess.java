package com.aem.geeks.core.workflows;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.Iterator;
import java.util.Set;

/*BhargavSeshadri - STEP:1 (coding related only step)- Custom Workflow Process
- When no existing process is available based on our requirement then we can write our own custon WF process.
- This "CUSTOM WF PROCESS" is available in the dropdown Of PROCESS STEP.

- WORK OF THIS "CUSTOM WF PROCESS" : We will provide some DATA in the "arguments" in the process step dialog box, and then when the WF executes the below code
                                     gets that data we have given in arguments and  add that data in the payload or page jcr:content node as props.
-
- For demo used this PROCESS in /conf/global/settings/workflow/models/bhargav-personal-workflow.html
* */

@Component(
        service = WorkflowProcess.class,
        immediate = true,
        property = {
                "process.label" + " = Bhargav Custom Workflow Process",  /*MANDITORY property: whatever label we have provided here, then with the same name this
                                                                    process will be available in the process step dropdown*/
                Constants.SERVICE_VENDOR + "= BhargavSeshadri",
                Constants.SERVICE_DESCRIPTION + " = Bhargav Custom  workflow Process."
        }
)
public class GeeksWorkflowProcess implements WorkflowProcess {

    private static final Logger log = LoggerFactory.getLogger(GeeksWorkflowProcess.class);

    //execute is a Manditory method -
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {

/* WorkflowSession: Using this we can get the backend objects, using this only we get the ResourceResolver, Session using this WorkflowSession.
*  MetaDataMap : If we pass any data to the dialog, that data we can get using this.(in case of custom WF process we get the data we have
                         given in "ARGUMENTS" process step dialog box)(in case of normal process step we can get the data we have given in dialog box of that step).
*  WorkItem : we might have the steps before and after our process step, and there will be an information and payload flowing from starting of our WF model till end
              . that payload and the other information should be available in our Custom WF Process and that all information is available in "WorkItem".
              apart from the available information we can also pass the other information as we want.
* */

        log.info("\n ================================================================================= ");
        try {
            WorkflowData workflowData = workItem.getWorkflowData();
            if (workflowData.getPayloadType().equals("JCR_PATH")) {
                Session session = workflowSession.adaptTo(Session.class); //getting the session using "WorkflowSession"

                //here we are getting the payload and then we are going to the /jcr:content node of that.
                String path = workflowData.getPayload().toString() + "/jcr:content";

                //converting that string path in to a Node and getting hold of that node.
                Node node = (Node) session.getItem(path);

                /*here we are getting the data we have provided as arguments, and we are splitting them using "," in case if multiple arguments are given  */
                String[] processArgs = processArguments.get("PROCESS_ARGS", "string").toString().split(",");
                for (String wfArgs : processArgs){
                    String[] args = wfArgs.split(":");  //here we are splitting the individual argument we have given "approver:bhargav" and storing them in an array.
                    String prop = args[0];
                    String value = args[1];
                    if(node!=null){
                        node.setProperty(prop, value);  //here we are adding the prop to the node
                    }
                }




//Using "WorkItem" we can also get the data we have given and some of the other data as well like WF Title, Arguments, previous steps info

                MetaDataMap WFData = workItem.getWorkflowData().getMetaDataMap();
                Set<String> keyset = WFData.keySet();
                Iterator<String> i = keyset.iterator();
                while (i.hasNext()){
                    String key = i.next();
                    log.info("\n ITEM KEY - {}", key, WFData.get(key));  /*in logs we are printing all the data that MetaDataMap can give to us. */
                }
                session.save();
            }
        }catch (Exception e){

        }
    }
}

