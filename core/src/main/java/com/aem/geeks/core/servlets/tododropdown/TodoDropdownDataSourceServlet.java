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

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "aemgeeks/components/datasources/todos",
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
                        options.add(optionFactory.create(request, todoItem.getTodo(), String.valueOf(todoItem.getId())));
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("Unable to load todo dropdown values from {}", TodoApiClient.TODOS_API_URL, e);
            options.add(optionFactory.create(request, "Unable to load todos", ""));
        }

        DataSource dataSource = new SimpleDataSource(options.iterator());
        request.setAttribute(DataSource.class.getName(), dataSource);
    }
}
