package com.aem.geeks.core.servlets.tododropdown;

import java.util.List;

//2
/*BhargavSeshadri - Helper Class -- TodosResponse -> represents the WHOLE API response
* So this class is like a complete json response object we get from the API call. It contains a list of TodoItem objects which represent individual todo items.
*
* Eg Json response :
* {
  "todos": [
    {
      "id": 1,
      "todo": "Learn Java"
    },
    {
      "id": 2,
      "todo": "Learn AEM"
    }
  ]
}
*
* So that main "todos" array in the json response is represented by this TodosResponse class and each object inside that array is represented by the TodoItem class.
* */
class TodosResponse {

    private List<TodoItem> todos;

    List<TodoItem> getTodos() {
        return todos;
    }
}
