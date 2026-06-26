package com.aem.geeks.core.models.impl;


import com.aem.geeks.core.models.BhargavBackendAemApisCompModel;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = BhargavBackendAemApisCompModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BhargavBackendAemApisCompModelImpl implements BhargavBackendAemApisCompModel {

    //Printing The Basic Dialog Fields Data
    @ValueMapValue
    private String personName;

    @ValueMapValue
    private String fatherName;

    @ValueMapValue
    private String averageIncome;

    @ValueMapValue
    private String[] multifieldvalues;

    @PostConstruct
    @Override
    public String postConstructMethod() {
        return "Post Construct Method is Executed";
    }


    @Override
    public String getPersonName() {
        return personName;
    }

    @Override
    public String getFatherName() {
        return fatherName;
    }

    @Override
    public String getAverageIncome() {
        return averageIncome;
    }

    @Override
    public String[] getMultifieldvalues() {
        return multifieldvalues;
    }


    /***********************************************USAGE OF BACKEND API's**********************/

    /*_______________Using Resource____________________
      Resource : Current Resource - Means where am I in the repository */

    //We can get the Resource using @ScriptVariable & @Inject also, by @SlingObject is preferrable
    @SlingObject
    private Resource resource;

    @Override
    public String getResourcePath() {
        return "Component Resource Path : " + resource.getPath();
    }

    @Override
    public String getResourceDetails() {

        //This will give the name of our component inside the page structure.
        String resourseName = resource.getName();

        //This will give the sling:resourceType of the component (or current resourse we are on).
        String compSlingResourceType = resource.getResourceType();

        Resource parentResourseDetails = resource.getParent();
        String parentResoursePath = resource.getParent().getPath();

        return "Resource Name : " + resourseName + "\n" + "Resource Type : " + compSlingResourceType + "\n"
                + "Parent Resource Details : " + parentResourseDetails + "\n" + "Parent Resource Path : " + parentResoursePath;
    }

    @Override
    public String isExpectedResourceType() {
        return "aemgeeks/components/bhargav-backend-aem-apis-comp - Is this the Sling:resourceType : " +
                resource.isResourceType("aemgeeks/components/bhargav-backend-aem-apis-comp");
    }

    //Getting hold of child resources
    @Override
    public List<Resource> getChildrenList() {

        List<Resource> childrenList = new ArrayList<>();
        Resource parentPath = resource.getParent();

        //Here I am going to the Parent because my Current Page have no Children.
        Iterator<Resource> iterator = parentPath.listChildren();

        while (iterator.hasNext()) {
            Resource child = iterator.next();
            childrenList.add(child);
        }
        return childrenList;
    }
    /*--------------------------------------------------------------------------------------------------------------------------*/




    /*_______________Using VALUEMAP____________________
     ValueMap : It only gives the properties which are present in the current resource. And it can't get hold of the props that
                are present in parent or child resources*/

    //    In Sling Model we can directly get the ValueMap like this also.
    //    @Inject
    //    private ValueMap valueMap;

    @Override
    public String getValueMapDetails() {

        //  Getting ValueMap using "Resource"
        ValueMap valueMap = resource.getValueMap();

        //we can print this prop value like this or like above using annotation.
        String personNameUsingValueMap = valueMap.get("personName", String.class);
        String lastModifiedBy = valueMap.get("jcr:lastModifiedBy", String.class);

        return "Person Name(Dialog field) printing using ValueMap : " + personNameUsingValueMap + "\n" + "lastModifiedBy : " + lastModifiedBy;
    }
    /*--------------------------------------------------------------------------------------------------------------------------*/





    /*_______________Using @ScriptVariable____________________
     *
     * ScriptVariable : using this we can get Page, PageManager, Resource, ResourceResolver, SlingHttpServletRequest,
     *                  SlingHttpServletResponse, ComponentContext, WCMMode  */

    //Getting Page Object
    @ScriptVariable
    private Page currentPage;

    @Override
    public String getCurrentPageDetails() {

        //Printing JCR:Title of a Page
        String jcrTitleOfCurrPage = currentPage.getTitle();

        String pagePath = currentPage.getPath();
        String parentPath = currentPage.getParent().getPath();

        // This will only print, If we give Page Title in the "Page Properties" of a page. Jcr:Title and this are not same.
        String pageTitle = currentPage.getPageTitle();

        String templateTitle = currentPage.getTemplate().getTitle();
        String templatePath = currentPage.getTemplate().getPath();
        String language = currentPage.getLanguage().toString();

        //Here we are directly getting hold of JCR:Content of the page
        String jcrContentResource = currentPage.getContentResource().getPath();

        Boolean isHideInNav = currentPage.isHideInNav();

        return "Jcr:Title : " + jcrTitleOfCurrPage + "\n" + "Page Path : " + pagePath + "\n" + "Parent Path : " + parentPath + "\n" + "Page Title : "
                + pageTitle + "\n" + "Template Title : " + templateTitle + "\n" + "Template Path : " + templatePath + "\n"
                + "JCR Content Resource Path : : " + jcrContentResource + "\n" + "Language of the Page : " + language + "\n" + "Is Hide in Nav : " + isHideInNav;

    }

    //Getting a ValueMap
    @Override
    public String getPropsUsingValueMap() {

        //Getting a ValueMap using Page Object and printing the Properties of the "Page".
        ValueMap allPageProperties = currentPage.getProperties();
        String pageJcrTitle = allPageProperties.get("jcr:title", String.class);
        String cqTemplate = allPageProperties.get("cq:template", String.class);

        return "Page Jcr:Title : " + pageJcrTitle + "\n" + "cqTemplate : " + cqTemplate;

    }

    @Override
    public List<String> childPageDetails() {

        List<String> childPagesList = new ArrayList<>();

        //Here We are going to "/content/aemgeeks/us/en"(Parent) from our current page and then we print all childs of "EN".
        Iterator<Page> childrenPages = currentPage.getParent().listChildren();
        while (childrenPages.hasNext()) {
            childPagesList.add(childrenPages.next().getPath());
        }
        return childPagesList;
    }
    /*--------------------------------------------------------------------------------------------------------------------------*/




    /*_______________Using PageManager Service API____________________
    * Getting PageManager using @ScriptVariable
    *  We Can Create, Delete, and Move the pages using this
    - pageManager.create(...);
    - pageManager.move(...);
    - pageManager.delete(...);*/

    @ScriptVariable
    private PageManager pageManager;

    @Override
    public String getPageDetailsUsingPageManager() {

        /* Using PageManager we can get the "Page" and from that we can get the details of that page.
        Getting the CurrentPage - we can do it in two ways
         1 - Directly Giving the path
        Page page = pageManager.getPage("/content/mysite/about");*/

        //2 - Using Resource
        Page page = pageManager.getContainingPage(resource);  //this will give the resource(we have give here) as a Page to us.
        String pageTitle = page.getTitle();

        String pagePath = pageManager.getContainingPage(resource).getPath();

        if (page != null) {
            return "Using PageManager - Page Jcr:Title : " + pageTitle + "\n" + "Page Path : " + pagePath;
        } else {
            return "Page not found";
        }
    }
    /*--------------------------------------------------------------------------------------------------------------------------*/




    /*_______________Using SlingHttpServletRequest____________________
     *
     * This Mainly gives us the all the info about the request like Url Info, query parameters, headers, selectors, attributes.
     *
     * Now to get the values for the below methods we have to give selectors and query parameters in the URL like
     * Eg: http://localhost:4502/content/aemgeeks/us/en/bhargav-backend-aem-apis-comp.selector1.selector2.html?name=bhargav
     * */

    @SlingObject
    private SlingHttpServletRequest request;

    @Override
    public String usingGetParameter() {

        /*Important : request.getParameter(...) only returns form/query parameters (name/value pairs in the query string or POST body). It does NOT read
                      HTTP headers or the request URL*/
        return "Query Parameter : " + request.getParameter("name") + "\n" + "Request URL(Complete page url with .html) : " + request.getRequestURI().toString();
    }

    @Override
    public String getRequestHeader() {

        //To get the request header(you can see those in page network tab)
        return "Request Headers - Accept-Language : " + request.getHeader("Accept-Language");
    }

    @Override
    public String[] getSelectors() {
        //Here we are getting the selectors and returning them.
        return request.getRequestPathInfo().getSelectors();
    }

    //This will give the component path.
    //eg : If we are on the page - /content/aemgeeks/us/en/jcr:content/root/container/bhargav-backend-aem-apis-comp.html
    @Override
    public String getPath() {
        return "Request Path(it gives Current Resource Path) : " + request.getRequestPathInfo().getResourcePath();
    }

}



