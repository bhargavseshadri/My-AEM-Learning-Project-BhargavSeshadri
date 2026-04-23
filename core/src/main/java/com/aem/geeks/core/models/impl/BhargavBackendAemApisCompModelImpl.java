package com.aem.geeks.core.models.impl;


import com.aem.geeks.core.models.BhargavBackendAemApisCompModel;
import com.day.cq.wcm.api.Page;
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
    public String postConstructMethod(){
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
        return "Resource Path : " + resource.getPath();
    }

    @Override
    public String getResourceDetails() {
        return "Resource Name : " + resource.getName() + "\n" + "Resource Type : " + resource.getResourceType() + "\n"
                + "Parent Resource Details : " + resource.getParent() + "\n" + "Parent Resource Path : " + resource.getParent().getPath();
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
        String parentPath = resource.getParent().toString();

        //Here I am going to the Parent because my Current Page have no Children.
        Iterator<Resource> iterator = resource.getParent().listChildren();

        while (iterator.hasNext()) {
            Resource child = iterator.next();
            childrenList.add(child);
        }
        return childrenList;
    }





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

        return "Person Name printing using ValueMap : " + personNameUsingValueMap + "\n" + "lastModifiedBy : " + lastModifiedBy;
    }





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
        String jcrTitle = currentPage.getTitle();

        //Here we are directly getting hold of JCR:Content of the page
        String contentResource = currentPage.getContentResource().getPath();

        String pagePath = currentPage.getPath();
        String parentPath = currentPage.getParent().getPath();
        String templateTitle = currentPage.getTemplate().getTitle();
        String templatePath = currentPage.getTemplate().getPath();
        String language = currentPage.getLanguage().toString();

        // This will only print, If we give Page Title in the "Page Properties" of a page. Jcr:Title and this are not same.
        String pageTitle = currentPage.getPageTitle();

        return "Jcr:Title : " + jcrTitle + "\n" + "Page Path : " + pagePath + "\n" + "Parent Path : " + parentPath + "\n" + "Page Title : "
                + pageTitle + "\n" + "Template Title : " + templateTitle + "\n" + "Template Path : " + templatePath + "\n"
                + "JCR Content Resource Path : : " + contentResource + "\n" + "Language of the Page : " + language;

    }

    @Override
    public String getPropsUsingValueMap() {

        //Getting a ValueMap using Page Object and printing the Properties of the Page.
        ValueMap allPageProperties = currentPage.getProperties();
        String jcrTitle = allPageProperties.get("jcr:title", String.class);
        String cqTemplate = allPageProperties.get("cq:template", String.class);

        return "Jcr:Title : " + jcrTitle + "\n" + "cqTemplate : " + cqTemplate;

    }

    @Override
    public List<String> childPageDetails() {

        List<String> childPagesList = new ArrayList<>();

        //Here We are going to "/content/aemgeeks/us/en"(Parent) from our current page and then we print all childs of "EN".
        Iterator<Page> childrenPages = currentPage.getParent().listChildren();
        while (childrenPages.hasNext()){
            childPagesList.add(childrenPages.next().getPath());
        }

        return childPagesList;
    }

}


















