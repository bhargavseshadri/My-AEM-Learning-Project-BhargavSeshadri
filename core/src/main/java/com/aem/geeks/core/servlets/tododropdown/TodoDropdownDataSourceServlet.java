package com.aem.geeks.core.servlets.tododropdown;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*BhargavSeshadri :
 * Step : 2 - Servlet executes and calls --> TodoApiClient.java
 * Helper classes flow 1 :: TodoDropdownDataSourceServlet.java --> TodoApiClient.java --> TodosResponse.java --> TodoItem.java
 * Helper classes flow 2 :: TodoDropdownDataSourceServlet.java --> TodoDropdownOptionFactory.java
 * */
@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "aemgeeks/components/datasources/todos", //This is kind of a dummy resourceType that we will use to bind this servlet to our datasource component in the dialog.
        methods = HttpConstants.METHOD_GET
)
public class TodoDropdownDataSourceServlet extends SlingSafeMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(TodoDropdownDataSourceServlet.class);

    private final TodoApiClient todoApiClient = new TodoApiClient();
    private final TodoDropdownOptionFactory optionFactory = new TodoDropdownOptionFactory();

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        List<Resource> options = new ArrayList<>();

        try {
            TodosResponse todosResponse = todoApiClient.fetchTodos();
            if (todosResponse != null && todosResponse.getTodos() != null) {
                for (TodoItem todoItem : todosResponse.getTodos()) {
                    if (todoItem.getId() != null && todoItem.getTodo() != null) {
                        //Here exactly we are setting the dropdown options for Granite UI. We are using our helper class TodoDropdownOptionFactory.
                        options.add(optionFactory.createDropdownOption(request, todoItem.getTodo(), todoItem.getTodo()));
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("Unable to load todo dropdown values from {}", TodoApiClient.TODOS_API_URL, e);
            options.add(optionFactory.createDropdownOption(request, "Unable to load todos", ""));
        }

        //Very important line
        /*At this point we have a list of dropdown options. But Granite UI only accepts a specific delivery format called:DATASOURCE
         * So the below line is like putting those options into the packaging format Granite understands.
         */
        DataSource dataSource = new SimpleDataSource(options.iterator());

        /*Here we are putting the DataSource with data in the request - using the request because Granite UI knows to look there. This is the magic connection.
        * Our servlet says:
            Hey Granite,
            here is the datasource.
            I put it in the request.

        * Then our dialog xml code does something similar to this - DataSource ds = request.getAttribute(DataSource.class.getName());, It retrieves the datasource your servlet stored.
        * Granite internally iterates over Resources one by one "for(Resource option : dataSource)" and reads the values and put it in the dialog dropdown.
        */
        request.setAttribute(DataSource.class.getName(), dataSource);
    }
}
