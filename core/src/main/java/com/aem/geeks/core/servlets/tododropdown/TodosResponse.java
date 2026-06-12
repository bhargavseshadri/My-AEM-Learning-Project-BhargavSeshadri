package com.aem.geeks.core.servlets.tododropdown;

import java.util.List;

class TodosResponse {

    private List<TodoItem> todos;

    List<TodoItem> getTodos() {
        return todos;
    }
}
