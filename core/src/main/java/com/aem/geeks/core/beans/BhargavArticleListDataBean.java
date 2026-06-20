package com.aem.geeks.core.beans;


//SeshadriBhargav - this is just a bean to store the values

/*Step: 3 - using java bean we set all the data we got in to bean fields - BhargavArticleListDataBean.java
Step : 2 - in this file we wrote all the query builder related code and get the required data - BhargavArticleListModelImpl.java
 */

public class BhargavArticleListDataBean {
    private String path;
    private String title;
    private String description;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
