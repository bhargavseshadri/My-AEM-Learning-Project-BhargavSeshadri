package com.aem.geeks.core.servlets.tododropdown;

//1
/*BhargavSeshadri - Helper Class -- TodoItem  -> represents ONE todo
* This class represents each object inside that main resposnse we get from API.
*
* Eg:
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
* */
class TodoItem {

    private Integer id;
    private String todo;

    Integer getId() {
        return id;
    }

    String getTodo() {
        return todo;
    }
}
