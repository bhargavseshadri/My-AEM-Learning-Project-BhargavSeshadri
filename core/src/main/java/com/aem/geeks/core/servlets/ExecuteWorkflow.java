package com.aem.geeks.core.servlets;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/*BhargavSeshadri - STEP:1(ONLY STEP) - TRIGGERING THE WORKFLOW USING CODE/API
*SERVLET WORK: here whenever we hit the servlet path by providing the qurey parameter(here we give our pagepath as a query param) then the servlet executes and then
                trigger the Workflow we have given here.

* IMP - TO TRIGGER THE SERVLET -> http://localhost:4502/bin/executeworkflow?page=/content/aemgeeks/us/en/bhargavseshadritestpage
* Then this servlet gets triggerrd and then it will go and trigger our WF. and based on our WF a new page version gets created.

* NOTE: Basically to trigger a workflow we need Two things 1.Workflow Model, 2.Payload(means the content path on which we want to run our WF,
*       like page, asset etc)
        IMP - Incase of triggering WF using code, then in that case to give which "WF MODEL" to execute  and on which "PAYLOAD", we will use "WorkflowSession"
* */



@Component(service = Servlet.class)
@SlingServletPaths(
        value = {"/bin/executeworkflow","/geeks/executeworkflow"}
)
public class ExecuteWorkflow extends SlingSafeMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ExecuteWorkflow.class);
    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        String status="Workflow Executing";

        final ResourceResolver resourceResolver = req.getResourceResolver(); //From request we are getting the ResourceResolver

        String payload=req.getRequestParameter("page").getString(); //here we are getting the QUERY PARAM(we give the page path as a query param)
        try {
            if(StringUtils.isNotBlank(payload)) {

                //MAIN - Here we are getting the "WorkflowSession" using ResourceResolver
                WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);

                // Here we are getting the WF model using the "WorkflowSession", here I am passing the runtime model of my WF.
                WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/bhargav-personal-workflow");

                //Using the WorkflowSession by using "newWorkflowData method" we are giving the PAYLOAD
                WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);

                // Now after we got the workflowModel, workflowData then we can start the WF using "startWorkflow method" from WorkflowSession.
                status=workflowSession.startWorkflow(workflowModel, workflowData).getState();
                //getState() : using this we are just getting the status of our workflow, and then we will print it as a response. not a manditory.

                /*NOTE:
                In most of the  cases we have to pass the metadata also to this startWorkflow method, then in that case we give
                => workflowSession.startWorkflow(workflowModel, workflowData,workflowMetadataMap);
                Basically this "workflowMetadataMap" is a hashmap with our data
                */
            }

        } catch (Exception e) {
            LOG.info("\n ERROR IN WORKFLOW {} ", e.getMessage());
        }
        resp.setContentType("application/json");
        resp.getWriter().write(status);
    }

}
