package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.beans.BhargavArticleListDataBean;
import com.aem.geeks.core.models.BhargavArticleListModel;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//SeshadriBhargav -
/*Step : 2 - in this file we wrote all the query builder related code and get the required data
* Step: 3 - using java bean we set all the data we got in to bean fields - BhargavArticleListDataBean.java
* Step: 1 - create a component - apps/aemgeeks/components/content/bhargav-articlelist-comp*/

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = BhargavArticleListModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class BhargavArticleListModelImpl implements BhargavArticleListModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(BhargavArticleListModelImpl.class);

    @ValueMapValue
    private String articleListRootPath;

    @SlingObject
    Resource resource;

    List<BhargavArticleListDataBean> bhargavArticleListDataBeanList;



    @Override
    public String getArticleListRootPath() {
        return articleListRootPath;
    }

    @PostConstruct
    protected void init() {
        ResourceResolver resourceResolver = resource.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        QueryBuilder builder = resourceResolver.adaptTo(QueryBuilder.class);  //here we are getting the queryBuilder API
        if (builder == null) {
            LOGGER.error("QueryBuilder is null");
            return;
        }
        Query query = null;

        Map<String, String> predicateMap = new HashMap<>();  //just creating a map and giving it our query
        predicateMap.put("path", articleListRootPath);
        predicateMap.put("type", "cq:Page");

        try {
            //Now here we are creating a query by giving the Map contains our query details and session
            query = builder.createQuery(PredicateGroup.create(predicateMap), session);

        } catch (Exception e) {
            LOGGER.debug("Error in Array List Component : {}", e);
        }

        if(query == null){
            return;
        }
        //this gives the results of our query. we get all the results
        SearchResult searchResult = query.getResult();

        //we are creating a ArrayList to store the BhargavArticleListDataBean objects data and we will send this list to our htl to render the data.
        bhargavArticleListDataBeanList = new ArrayList<>();

        //Hit represents a single search result. means each page path we get in result is a hit. so here we are getting a list of hits using getHits() and looping
        for(Hit hit : searchResult.getHits()){

            //Creating out bean object to set the data, we are creating this inside for loop because for every hit a object instance gets created
            BhargavArticleListDataBean bhargavArticleListDataBeanObj = new BhargavArticleListDataBean();

            String path = null;

            try{
                path = hit.getPath();
                Resource articleResource = resourceResolver.getResource(path);
                Page articlePage = articleResource.adaptTo(Page.class);
                String title = articlePage.getTitle();
                String description = articlePage.getDescription();
                LOGGER.debug("Article List comp - Title:{}, Description:{}", title, description);

                bhargavArticleListDataBeanObj.setPath(path);
                bhargavArticleListDataBeanObj.setTitle(title);
                bhargavArticleListDataBeanObj.setDescription(description);

                //After setting the value to the fields in the bean class we are putting that objects in to our ArrayList
                bhargavArticleListDataBeanList.add(bhargavArticleListDataBeanObj);

            } catch (Exception e) {
                LOGGER.debug("Error in Array List Component : {}", e);
            }

        }
    }

    //this getter will return the ArrayList contains all the objects
    @Override
    public List<BhargavArticleListDataBean> getBhargavArticleListDataBeanList() {
        return bhargavArticleListDataBeanList;
    }
}
